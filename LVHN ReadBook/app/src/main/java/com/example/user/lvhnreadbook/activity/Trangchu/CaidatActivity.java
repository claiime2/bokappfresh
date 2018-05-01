package com.example.user.lvhnreadbook.activity.Trangchu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.lvhnreadbook.ultil.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class CaidatActivity extends AppCompatActivity {
   private Context mContext = CaidatActivity.this;


    ArrayList<String> arrayList1;
    ListView listView1;
    ArrayAdapter arrayAdapter1;

    ArrayList<String> arrayList2;
    ListView listView2;
    ArrayAdapter arrayAdapter2;

    Button btndangxuat;
    Button back;
    private static final int ACTIVITY_NUM=4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.user.lvhnreadbook.R.layout.activity_caidat);
        initView();
        listviewsk();
        back();
        dangxuat();
        setupBottomNavigationView();
    }

    private void back() {
        back=findViewById(com.example.user.lvhnreadbook.R.id.button_backcaidat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(CaidatActivity.this,MainActivity.class);
                startActivity(back);
            }
        });

    }
    private void dangxuat(){
        btndangxuat=findViewById(com.example.user.lvhnreadbook.R.id.btn_dangxuat);
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CaidatActivity.this,"Ban da dang xuat",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public  void initView(){

    listView1 = (ListView) findViewById(com.example.user.lvhnreadbook.R.id.listview_caidat1);
    listView2 = (ListView) findViewById(com.example.user.lvhnreadbook.R.id.listview_caidat2);
}

    public void listviewsk(){

        btndangxuat=(Button)findViewById(com.example.user.lvhnreadbook.R.id.btn_dangxuat);
        arrayList1=new ArrayList<>();
        arrayList1.add("Thay đổi thông tin cá nhân");
        arrayList1.add("Cài đặt trình đọc sách");
        arrayList1.add("Mời bạn bè");
        arrayList1.add("Những sách đang đọc");
        //
        arrayAdapter1=new ArrayAdapter(CaidatActivity.this,android.R.layout.simple_list_item_1,arrayList1);
        listView1.setAdapter(arrayAdapter1);
        arrayList2=new ArrayList<>();
        arrayList2.add("Câu hỏi thường gặp");
        arrayList2.add("Điều khoản sử dụng");
        arrayList2.add("Liên hệ");
        arrayList2.add("Theo dõi chúng tôi trên facebook");
        //
        arrayAdapter2=new ArrayAdapter(CaidatActivity.this,android.R.layout.simple_list_item_1,arrayList2);
        listView2.setAdapter(arrayAdapter2);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CaidatActivity.this,"deomo sussucess",Toast.LENGTH_SHORT).show();

            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CaidatActivity.this,"deomo sussucess",Toast.LENGTH_SHORT).show();

            }
        });

    }



    public void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(com.example.user.lvhnreadbook.R.id.bottom_Na);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}

