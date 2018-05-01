package com.example.user.lvhnreadbookapp.activity.ForumPost;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import  android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.Models.BaiPostModel;
import com.example.user.lvhnreadbookapp.Models.ThanhvienModel;
import com.example.user.lvhnreadbookapp.adapter.BinhluanAdapter;
import com.example.user.lvhnreadbookapp.ultil.Constant;
import com.example.user.lvhnreadbookapp.Models.BinhluanPostModel;
import com.example.user.lvhnreadbookapp.R;
import com.example.user.lvhnreadbookapp.adapter.BinhluanPostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BinhluanPostActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<BinhluanPostModel> blPostList;
    BinhluanPostAdapter binhluanPostAdapter;
    Toolbar toolbar;
    BinhluanPostModel rootPost;
    String tieude,sotl;
    EditText binhluan;
    ImageView send;
    DatabaseReference database;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ThanhvienModel thanhvien;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binhluan_forum);

        intview();
        actionbar();
        addListview();

    }
    public void intview(){
        listView=findViewById(R.id.listviewbinhluanpost);
        toolbar=findViewById(R.id.toolbar_nav);
        binhluan=findViewById(R.id.binhluan);
        send=findViewById(R.id.send);
        firebaseAuth=FirebaseAuth.getInstance();
        blPostList=new ArrayList<>();
        user=firebaseAuth.getCurrentUser();
        sotl=getIntent().getStringExtra("sotl");
        tieude=getIntent().getStringExtra("title");
        //Toast.makeText(BinhluanPostActivity.this,sotl+"saupuut",Toast.LENGTH_SHORT).show();
        rootPost=getIntent().getParcelableExtra("modelfirst");
        //Toast.makeText(BinhluanPostActivity.this,rootPost.getMablp(),Toast.LENGTH_SHORT).show();
       blPostList =getIntent().getParcelableArrayListExtra("postModel");


blPostList.add(0,rootPost);


       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
              // Date date=new Date();
               Calendar calendar=Calendar.getInstance();
               String strDate=dateFormat.format(calendar.getTime()).toString();
              // Toast.makeText(BinhluanPostActivity.this,strDate,Toast.LENGTH_SHORT).show();
               String text=binhluan.getText().toString().trim();
               if(text.length()>0&&user!=null){
                   database=FirebaseDatabase.getInstance().getReference().child("binhluanpost").child(rootPost.getMablp()).push();
                   database.child("mauser").setValue(user.getUid());
                   database.child("noidung").setValue(text);
                   database.child("ngay").setValue(strDate);
                  // Toast.makeText(BinhluanPostActivity.this,sotl+"binhluan",Toast.LENGTH_SHORT).show();
                   DatabaseReference database2=FirebaseDatabase.getInstance().getReference().child("baiposts").child(rootPost.getMablp());
                   database2.child("sotraloi").setValue(Long.parseLong(sotl)+1);
                  // Toast.makeText(BinhluanPostActivity.this,(Long.parseLong(sotl)+1)+"sauaddbinhluan",Toast.LENGTH_SHORT).show();
                   DatabaseReference finduser=FirebaseDatabase.getInstance().getReference();

                   finduser.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                            thanhvien=dataSnapshot.child("thanhviens").child(user.getUid()).getValue(ThanhvienModel.class);
                            thanhvien.setMaso(dataSnapshot.getKey());
                           DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                           // Date date=new Date();
                           Calendar calendar=Calendar.getInstance();
                           String strDate=dateFormat.format(calendar.getTime()).toString();
                           // Toast.makeText(BinhluanPostActivity.this,strDate,Toast.LENGTH_SHORT).show();
                           String text=binhluan.getText().toString().trim();
                           Intent intent=new Intent(BinhluanPostActivity.this,BinhluanPostActivity.class);
                           intent.putExtra("modelfirst",rootPost);
                           intent.putExtra("sotl",String.valueOf(sotl));
                           intent.putExtra("title",tieude);
                           BinhluanPostModel binhluanPostModel=new BinhluanPostModel();
                           binhluanPostModel.setNoidung(text);
                           binhluanPostModel.setNgay(strDate);
                           binhluanPostModel.setThanhvienModel(thanhvien);
                           blPostList.remove(0);
                           blPostList.add(binhluanPostModel);
                           intent.putParcelableArrayListExtra("postModel",blPostList);

                           Toast.makeText(BinhluanPostActivity.this,"Đã gửi",Toast.LENGTH_SHORT).show();
                           startActivity(intent);
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });


               }else{
                   if(user==null){
                       Toast.makeText(BinhluanPostActivity.this,"Đăng nhập trước khi bình luận",Toast.LENGTH_SHORT).show();
                   }
               }
           }
       });
          }
    public void actionbar(){
        toolbar.setTitle(tieude);
        toolbar.setBackgroundColor(Constant.color);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(BinhluanPostActivity.this,PostbaiActivity.class);
                startActivity(intent);
            }
        });

    }
    public void addListview(){//ten,anh,nd,ngay
        binhluanPostAdapter=new BinhluanPostAdapter(this,R.layout.item_binhluan_forum,blPostList);
        listView.setAdapter(binhluanPostAdapter);
    }
}
