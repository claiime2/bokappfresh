package com.example.user.lvhnreadbook.activity.ForumPost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.lvhnreadbook.R;
import com.example.user.lvhnreadbook.activity.Trangchu.CaidatTimeNav;
import com.example.user.lvhnreadbook.activity.Trangchu.MainActivity;
import com.example.user.lvhnreadbook.ultil.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {
Toolbar toolbar;
Button postbai;
EditText tieude,noidung;
DatabaseReference databaseReference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        toolbar=findViewById(R.id.toolbar_nav);
        tieude=findViewById(R.id.tieude);
        noidung=findViewById(R.id.noidung);
        postbai=findViewById(R.id.btnPostbai);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("baiposts");
        actionPostadd();

        actionbar();

    }
    public void actionPostadd(){
        user= FirebaseAuth.getInstance().getCurrentUser();
        postbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String strDate=dateFormat.format(calendar.getTime()).toString();
                Toast.makeText(AddPostActivity.this,strDate,Toast.LENGTH_SHORT).show();
                String tieudePost=tieude.getText().toString();
                String noidungPost=noidung.getText().toString();
                databaseReference=FirebaseDatabase.getInstance().getReference().child("baiposts").push();
                databaseReference.child("tieude").setValue(tieudePost);
                databaseReference.child("mauser").setValue(user.getUid());
                databaseReference.child("noidungpost").setValue(noidungPost);
                databaseReference.child("sotraloi").setValue(0);
                databaseReference.child("ngaytao").setValue(strDate);
                Toast.makeText(AddPostActivity.this,"Đăng thành công",Toast.LENGTH_SHORT).show();


            }
        });
    }
    public void actionbar(){
        toolbar.setTitle("Đăng bài");
        toolbar.setBackgroundColor(Constant.color);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AddPostActivity.this,PostbaiActivity.class);
                startActivity(intent);
            }
        });

    }
}
