package com.example.user.lvhnreadbookapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


import com.example.user.lvhnreadbookapp.ultil.BottomNavigationViewHelper;
import com.example.user.lvhnreadbookapp.ultil.Constant;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by User on 26/03/2018.
 */

public class CanhanActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM=4;
    private Context mContext = CanhanActivity.this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_canhan);
        setupBottomNavigationView();

    }


    public void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(R.id.bottom_Na);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
