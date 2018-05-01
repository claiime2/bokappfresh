package com.example.user.lvhnreadbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.user.lvhnreadbook.ultil.Constant;

/**
 * Created by User on 26/03/2018.
 */

public class SachdadocActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.activity_sachdadoc);
    }

}
