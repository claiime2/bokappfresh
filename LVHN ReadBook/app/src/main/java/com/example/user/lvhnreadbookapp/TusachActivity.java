package com.example.user.lvhnreadbookapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.ultil.BottomNavigationViewHelper;
import com.example.user.lvhnreadbookapp.ultil.Constant;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by User on 25/03/2018.
 */

public class TusachActivity extends AppCompatActivity {
    private Context mContext = TusachActivity.this;
    private static final int ACTIVITY_NUM=0;
//    final String DATABASE_NAME = "sachdb.sqlite";
    SQLiteHelper database;
    Toolbar toolbar;
    ListView listView;
    ArrayList<SachModel> listsach;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_tusach);
        setupBottomNavigationView();
        addControls();
//        ReadData();
        Actionbar();
    }
        public void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(R.id.bottom_Na);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void addControls() {
        RecyclerView myrv = (RecyclerView) findViewById(R.id.viewtusach);
        toolbar = findViewById(R.id.toolbar_tusach);
        listsach = new ArrayList<>();
        database=SQLiteHelper.getHeler(this);

        recyclerViewAdapter = new RecyclerViewAdapter(this,listsach);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(recyclerViewAdapter);
        database.loadData(listsach);
        recyclerViewAdapter.notifyDataSetChanged();
    }

}
