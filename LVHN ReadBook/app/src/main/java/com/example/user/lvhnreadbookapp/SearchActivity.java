package com.example.user.lvhnreadbookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.ultil.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private SearchView svTimKiem;
    private TextView tvTimKiem;
    private ListView lvTimKiem;
    int count=0;
    boolean flag=false;
    private DatabaseReference database;
    String[] unicode = new String[] { "a", "á", "à", "ả",
            "ã", "ạ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ",
            "ẫ", "ậ", "o", "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ",
            "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ", "e", "é", "è", "ẻ",
            "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ", "i", "í", "ì", "ỉ",
            "ĩ", "ị", "u", "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử",
            "ữ", "ự", "y", "ý", "ỳ", "ỷ", "ỹ", "ỵ", "đ", "A", "Á", "À",
            "Ả", "Ã", "Ạ", "Ă", "Ắ", "Ằ", "Ẳ", "Ẵ", "Ặ", "Â", "Ấ", "Ầ",
            "Ẩ", "Ẫ", "Ậ", "O", "Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", "Ồ",
            "Ổ", "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", "Ở", "Ỡ", "Ợ", "E", "É", "È",
            "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế", "Ề", "Ể", "Ễ", "Ệ", "I", "Í", "Ì",
            "Ỉ", "Ĩ", "Ị", "U", "Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", "Ừ",
            "Ử", "Ữ", "Ự", "Y", "Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ", "Đ" };
    String[] ascii = new String[] { "a", "a", "a", "a", "a",
            "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
            "a", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o",
            "o", "o", "o", "o", "o", "o", "o", "e", "e", "e", "e", "e",
            "e", "e", "e", "e", "e", "e", "e", "i", "i", "i", "i", "i",
            "i", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u",
            "u", "y", "y", "y", "y", "y", "y", "d", "A", "A", "A", "A",
            "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
            "A", "A", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O",
            "O", "O", "O", "O", "O", "O", "O", "O", "E", "E", "E", "E",
            "E", "E", "E", "E", "E", "E", "E", "E", "I", "I", "I", "I",
            "I", "I", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U",
            "U", "U", "Y", "Y", "Y", "Y", "Y", "Y", "D" };
    public String toASCII(String str){
        char[] chs = str.toCharArray();
        String newStr = "";

        for(int i = 0; i < chs.length; i++){
            char my = chs[i];

            for(int j = 0; j < ascii.length; j++){
                char u = unicode[j].charAt(0);

                if(u == my){
                    my = ascii[j].charAt(0);
                    break;
                }
            }

            newStr += my;
        }

        return newStr;
    }

    String search;
    public void chuyen(View v){
        SachModel sachModel=new SachModel();
        int pos=lvTimKiem.getPositionForView(v);
        sachModel=(SachModel) lvTimKiem.getItemAtPosition(pos);
        Intent intent=new Intent(SearchActivity.this,ChitietsachActivity.class);
        intent.putExtra("keysach",sachModel.getMasach());
        intent.putExtra("keytheloai",sachModel.getMatheloai());
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database=FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_timkiem);


        svTimKiem=(SearchView) findViewById(R.id.svTimKiem);
        tvTimKiem=(TextView) findViewById(R.id.tvTimKiem);
        lvTimKiem=(ListView) findViewById(R.id.lvTimKiem);
        svTimKiem.setSubmitButtonEnabled(true);
        svTimKiem.setQueryHint("Nhập tên sách...");
        final ArrayList<SachModel> data=new ArrayList<>();
        final TimKiemAdapter adapter=new TimKiemAdapter(this,R.layout.item_timkiem,data);
        lvTimKiem.setAdapter(adapter);
        svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                data.clear();
                adapter.notifyDataSetChanged();
                count=0;
                if (query.trim().length()!=0){
                    database.child("sachs").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    String name=(String) singleSnapshot.child("TenSach").getValue();
                                String theloai=singleSnapshot.getKey();
                                for(DataSnapshot theloaiSnapshot: singleSnapshot.getChildren()){
                                    String s1=toASCII(theloaiSnapshot.child("tensach").getValue().toString().toLowerCase());
                                    String s2=toASCII(query.toLowerCase());
                                    if(s1.contains(s2)==true){
                                        for(int i=0;i<data.size();i++){
                                            if(data.get(i).getMasach().equalsIgnoreCase(theloaiSnapshot.getKey().toString())){
                                                flag=true;
                                            }
                                        }
                                        if(flag==true){
                                            flag=false;
                                            continue;
                                        }
                                        count++;
                                        SachModel sachModel=theloaiSnapshot.getValue(SachModel.class);
                                        sachModel.setMatheloai(theloai);
                                        sachModel.setMasach(theloaiSnapshot.getKey());
                                        data.add(sachModel);
                                        adapter.notifyDataSetChanged();
                                        String s="Tìm thấy "+String.valueOf(count)+" kết quả";
                                        tvTimKiem.setText(s);
                                    }
                                }
                            }
                            if (count==0){
                                tvTimKiem.setText("Không tìm thấy kết quả");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    count=0;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                return true;
            }
        });
    }
}


