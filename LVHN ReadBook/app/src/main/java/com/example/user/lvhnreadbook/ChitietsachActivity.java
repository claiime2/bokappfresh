package com.example.user.lvhnreadbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.lvhnreadbook.ultil.Constant;

/**
 * Created by User on 25/03/2018.
 */

public class ChitietsachActivity extends AppCompatActivity {
    Button btChiTiet;
    String masach;
    String matheloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsach);
        setTheme(Constant.theme);
        masach=getIntent().getStringExtra("keysach");
        Toast.makeText(this,"mã sách "+masach,Toast.LENGTH_SHORT).show();
        matheloai=getIntent().getStringExtra("keytheloai");
        Toast.makeText(this,"theloai "+matheloai ,Toast.LENGTH_SHORT).show();

    }
    public void chuyen(View view){
        String thongtin="";
        Intent intent=new Intent(ChitietsachActivity.this,DocsachActivity.class);
        intent.putExtra("key",thongtin);

        startActivity(intent);
    }
}
