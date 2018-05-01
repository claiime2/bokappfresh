package com.example.user.lvhnreadbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.lvhnreadbook.ultil.BottomNavigationViewHelper;
import com.example.user.lvhnreadbook.ultil.Constant;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by User on 25/03/2018.
 */

public class TusachActivity extends AppCompatActivity {
    private Context mContext = TusachActivity.this;
    private static final int ACTIVITY_NUM=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_tusach);
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
