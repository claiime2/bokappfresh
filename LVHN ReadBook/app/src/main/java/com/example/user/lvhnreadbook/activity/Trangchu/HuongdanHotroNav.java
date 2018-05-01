package com.example.user.lvhnreadbook.activity.Trangchu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import  android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.example.user.lvhnreadbook.ultil.Constant;
import com.example.user.lvhnreadbook.adapter.HuongdanHotroAdapter;
import com.example.user.lvhnreadbook.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HuongdanHotroNav extends AppCompatActivity {
    Toolbar toolbar;
    ExpandableListView exListview;
    List<String> listdataQuestion;
    HashMap<String,String> listdata;
    HuongdanHotroAdapter huongdanHotroAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.nav_huongdan_hotro);
        initView();
        actionbar();
        //int width = metrics.widthPixels;
        databaseReference= FirebaseDatabase.getInstance().getReference().child("cauhois");
        //exListview.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    listdataQuestion.add(data.child("hoi").getValue().toString());
                    listdata.put(listdataQuestion.get(i),data.child("traloi").getValue().toString());
                    i++;
                    huongdanHotroAdapter=new HuongdanHotroAdapter(HuongdanHotroNav.this,listdataQuestion,listdata);
                    exListview.setAdapter(huongdanHotroAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void initView(){
        toolbar=findViewById(R.id.toolbar_nav);
        exListview=findViewById(R.id.listcauhoi);
        listdataQuestion=new ArrayList<>();
        listdata=new HashMap<String,String>();
       setSupportActionBar(toolbar);

    }
    public void actionbar(){
       toolbar.setTitle("Câu hỏi thường gặp");
        toolbar.setBackgroundColor(Constant.color);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(HuongdanHotroNav.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


}
