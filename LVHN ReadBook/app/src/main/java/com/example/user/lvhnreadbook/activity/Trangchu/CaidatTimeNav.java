package com.example.user.lvhnreadbook.activity.Trangchu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.lvhnreadbook.ultil.Constant;
import com.example.user.lvhnreadbook.R;

public class CaidatTimeNav extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.nav_caidat_time);
        initView();
        actionbar();
    }
    public void initView(){
        toolbar=findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void actionbar(){
        toolbar.setTitle("Cài đặt giờ");
        toolbar.setBackgroundColor(Constant.color);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(CaidatTimeNav.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
