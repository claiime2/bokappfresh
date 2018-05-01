package com.example.user.lvhnreadbook.activity.ForumPost;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.lvhnreadbook.ultil.BottomNavigationViewHelper;
import com.example.user.lvhnreadbook.ultil.Constant;
import com.example.user.lvhnreadbook.R;
import com.example.user.lvhnreadbook.adapter.ViewPagerAdapterPostbai;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class PostbaiActivity extends AppCompatActivity{
    private static final int ACTIVITY_NUM=3;
    private Context mContext = PostbaiActivity.this;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    FloatingActionButton addPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_baipost);
        tabLayout=findViewById(R.id.tab_postbai);
        viewPager=findViewById(R.id.viewpager_postpai);
        toolbar=findViewById(R.id.toolbar_nav);
        //addPost=findViewById(R.id.add_post);
        ViewPagerAdapterPostbai viewPagerAdapterPostbai=new ViewPagerAdapterPostbai(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapterPostbai);///
        viewPagerAdapterPostbai.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        setupBottomNavigationView();
        actionbar();
//        addPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
    public void actionbar(){
        toolbar.setTitle("                 Giao lưu đọc sách");


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
