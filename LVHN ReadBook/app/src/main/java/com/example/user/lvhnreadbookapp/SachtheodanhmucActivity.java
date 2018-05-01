package com.example.user.lvhnreadbookapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.ultil.Constant;

public class SachtheodanhmucActivity extends AppCompatActivity {
    private Context mContext = SachtheodanhmucActivity.this;
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(Constant.theme);
        setContentView(R.layout.activity_sachtheodanhmuc);
        key= getIntent().getStringExtra("keydanhmuc");//khóa gửi qua
        Toast.makeText(this,key,Toast.LENGTH_SHORT).show();
    }
}
