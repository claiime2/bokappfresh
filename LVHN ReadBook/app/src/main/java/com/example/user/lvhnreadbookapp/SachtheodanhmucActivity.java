package com.example.user.lvhnreadbookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.Models.DanhMuc;
import com.example.user.lvhnreadbookapp.Models.DanhmucInterface;
import com.example.user.lvhnreadbookapp.Models.SachInterface;
import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.activity.Trangchu.HuongdanHotroNav;
import com.example.user.lvhnreadbookapp.activity.Trangchu.MainActivity;
import com.example.user.lvhnreadbookapp.adapter.DanhmucnoibatAdapter;
import com.example.user.lvhnreadbookapp.adapter.HorizontalAdapter;
import com.example.user.lvhnreadbookapp.adapter.RecycleMainAdapter;
import com.example.user.lvhnreadbookapp.adapter.SachtheodanhmucAdapter;
import com.example.user.lvhnreadbookapp.ultil.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SachtheodanhmucActivity extends AppCompatActivity {
    private Context mContext = SachtheodanhmucActivity.this;
    SachtheodanhmucAdapter adapter;
    ArrayList<SachModel> listsach;
    String key,vitri;
    SachModel sachModel;
    GridView gridView;
    Toolbar toolbar;
    DanhMuc danhMuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_sachtheodanhmuc);
        gridView=findViewById(R.id.grid_sachtheodm);
        key= getIntent().getStringExtra("keydanhmuc");//khóa gửi qua
        vitri=getIntent().getStringExtra("vitri");
        Toast.makeText(this,key,Toast.LENGTH_SHORT).show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(SachtheodanhmucActivity.this,ChitietsachActivity.class);
                intent.putExtra("keysach",listsach.get(position).getMasach());
                intent.putExtra("keytheloai",listsach.get(position).getMatheloai());
                startActivity(intent);
//                Toast.makeText(SachtheodanhmucActivity.this,listsach.get(position).getMatheloai().toString(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(SachtheodanhmucActivity.this,listsach.get(position).getMasach().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        actionbar();
        addGridview();

    }
    public void actionbar(){
        toolbar=findViewById(R.id.toolbar_nav);
        toolbar.setTitle("Danh sách sách");
        toolbar.setBackgroundColor(Constant.color);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(vitri.equals("trangchu")){
                    intent =new Intent(SachtheodanhmucActivity.this,MainActivity.class);
                }else{
                   intent =new Intent(SachtheodanhmucActivity.this,DanhmucnoibatActvity.class);
                }

                startActivity(intent);
            }
        });

    }
    public void addGridview(){
        sachModel=new SachModel();
        listsach=new ArrayList<>();

        adapter=new SachtheodanhmucAdapter(this,R.layout.gridview_row,listsach);
        gridView.setAdapter(adapter);
        switch (key){
            case "Sách mới cập nhật":
                getsachmoi();
                break;
            case "Sách đọc nhiều":
                getdocnhieu();
                break;
            case "Sách đề cử":
                getsachdecu();
                break;
            default:
                gettheloai();
                break;

        }


    }
    public void getsachmoi(){

        final SachInterface sachInterface= new SachInterface() {
            @Override
            public void getSachModel(SachModel sachModel) {
                listsach.add(sachModel) ;
                adapter.notifyDataSetChanged();
                sortDay(listsach);

            }
        };

        sachModel.getDataSach().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listsach.clear();
                sachModel.getdataSach(sachInterface);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public  void getsachdecu(){

        final SachInterface sachInterface= new SachInterface() {
            @Override
            public void getSachModel(SachModel sachModel) {
                listsach.add(sachModel) ;
                adapter.notifyDataSetChanged();
                sortdanhgia(listsach);
            }
        };

        sachModel.getDataSach().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listsach.clear();
                sachModel.getdataSach(sachInterface);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void getdocnhieu(){

        final SachInterface sachInterface= new SachInterface() {
            @Override
            public void getSachModel(SachModel sachModel) {
                listsach.add(sachModel) ;
                adapter.notifyDataSetChanged();
                sortLuotxem(listsach);


            }
        };

        sachModel.getDataSach().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listsach.clear();
                sachModel.getdataSach(sachInterface);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void gettheloai(){
        danhMuc=new DanhMuc();
        listsach=new ArrayList<>();

        adapter=new SachtheodanhmucAdapter(this,R.layout.gridview_row,listsach);
        gridView.setAdapter(adapter);

        final SachInterface sachInterface= new SachInterface() {
            @Override
            public void getSachModel(SachModel sachModel) {
                listsach.add(sachModel) ;
                adapter.notifyDataSetChanged();
                sortDay(listsach);

            }
        };
        sachModel.getDataSach().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listsach.clear();
                sachModel.getdataMatheloai(sachInterface,key);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sortDay(ArrayList<SachModel> items){//ngay xem moi nhat
        Collections.sort(items, new Comparator<SachModel>() {
            DateFormat f=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            @Override
            public int compare(SachModel o1,SachModel o2) {
                try {
                    return f.parse(o2.getNgaydang()).compareTo(f.parse(o1.getNgaydang()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

    }
    public void sortLuotxem(ArrayList<SachModel> items){
        Collections.sort(items, new Comparator<SachModel>() {
            @Override
            public int compare(SachModel o1,SachModel o2) {
                if (o1.getLuotxem() > o2.getLuotxem()) return -1;
                if (o1.getLuotxem() > o2.getLuotxem()) return 1;
                return 0;

            }
        });

    }
    public void sortdanhgia(ArrayList<SachModel> items){
        Collections.sort(items, new Comparator<SachModel>() {
            @Override
            public int compare(SachModel o1,SachModel o2) {
                if (o1.getDanhgia() > o2.getDanhgia()) return -1;
                if (o1.getDanhgia() > o2.getDanhgia()) return 1;
                return 0;

            }
        });

    }

}
