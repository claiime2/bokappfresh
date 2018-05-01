package com.example.user.lvhnreadbook.ultil;

/**
 * Created by User on 16/03/2018.
 */



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.user.lvhnreadbook.CanhanActivity;
import com.example.user.lvhnreadbook.DanhmucnoibatActvity;
import com.example.user.lvhnreadbook.R;
import com.example.user.lvhnreadbook.TusachActivity;
import com.example.user.lvhnreadbook.activity.ForumPost.PostbaiActivity;
import com.example.user.lvhnreadbook.activity.Trangchu.MainActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;



public class BottomNavigationViewHelper {
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        //bottomNavigationViewEx.setTextVisibility(false);
    }
    public static void enableNavigation(final Context context, final Activity callingActivity,BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()){
                case R.id.bottom_tusach: //Activity =0

                    Intent inten0=new Intent(context,TusachActivity.class);
                    context.startActivity(inten0);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case R.id.bottom_danhsach: //Activity =1

                    Intent inten1=new Intent(context,DanhmucnoibatActvity.class);
                    context.startActivity(inten1);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case R.id.bottom_trangchu: //Activity =2

                    Intent inten2=new Intent(context,MainActivity.class);
                    context.startActivity(inten2);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                case R.id.bottom_post:////Activity =3
                   ;
                    Intent inten4=new Intent(context,PostbaiActivity.class);
                    context.startActivity(inten4);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case R.id.bottom_canhan: //Activity =4

                    Intent inten3=new Intent(context,CanhanActivity.class);
                    context.startActivity(inten3);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    break;
            }
            return false;

            }
        });

    }
}