package com.example.user.lvhnreadbookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.Models.BaiPostInterface;
import com.example.user.lvhnreadbookapp.Models.BaiPostModel;
import com.example.user.lvhnreadbookapp.Models.BinhluanPostModel;
import com.example.user.lvhnreadbookapp.Models.DanhMuc;
import com.example.user.lvhnreadbookapp.Models.DanhmucInterface;
import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.activity.ForumPost.BinhluanPostActivity;
import com.example.user.lvhnreadbookapp.adapter.BaiPostAdapter;
import com.example.user.lvhnreadbookapp.adapter.DanhmucnoibatAdapter;
import com.example.user.lvhnreadbookapp.ultil.BottomNavigationViewHelper;
import com.example.user.lvhnreadbookapp.ultil.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by User on 26/03/2018.
 */

public class DanhmucnoibatActvity extends AppCompatActivity {
    private Context mContext = DanhmucnoibatActvity.this;
    private static final int ACTIVITY_NUM=1;
    Button btnTruyencuoi;
    DanhMuc danhMuc;
    GridView gridView;
    ArrayList<DanhMuc> danhMucList;
    DanhmucnoibatAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_danhmuc);
        gridView=findViewById(R.id.gridviewDanhmuc);
        setupBottomNavigationView();



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(DanhmucnoibatActvity.this,SachtheodanhmucActivity.class);
                intent.putExtra("keydanhmuc",danhMucList.get(position).getMatheloai().toString());
                intent.putExtra("vitri","danhmuc");
                //Toast.makeText(DanhmucnoibatActvity.this,danhMucList.get(position).getMatheloai().toString(),Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
        addListView();

    }
    public void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(R.id.bottom_Na);;
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(DanhmucnoibatActvity.this,this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
    public void addListView() {//tieude,anhuser,tenuser,ngaytao,sotl
        danhMuc=new DanhMuc();
        danhMucList=new ArrayList<>();

        adapter=new DanhmucnoibatAdapter(this,R.layout.item_danhmuc_gridview,danhMucList);
        gridView.setAdapter(adapter);

        final DanhmucInterface danhmucInterface= new DanhmucInterface() {
            @Override
            public void getDanhmuc(DanhMuc danhmuc) {
                danhMucList.add(danhmuc) ;
                adapter.notifyDataSetChanged();

            }
        };

        danhMuc.getDataTheloai().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucList.clear();
                danhMuc.getdataTheloai(danhmucInterface);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
