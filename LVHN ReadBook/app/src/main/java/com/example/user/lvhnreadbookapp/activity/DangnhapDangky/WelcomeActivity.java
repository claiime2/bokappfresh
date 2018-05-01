package com.example.user.lvhnreadbookapp.activity.DangnhapDangky;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.lvhnreadbookapp.R;
import com.example.user.lvhnreadbookapp.activity.DangnhapDangky.LoginActivity;

public class WelcomeActivity extends Activity {
    private ImageView img;
    private TextView txtLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        img=(ImageView) findViewById(R.id.img_logo);
        txtLogo=(TextView) findViewById(R.id.txt_logo);
        try {
            PackageInfo packageInfo= getPackageManager().getPackageInfo(getPackageName(),0);
            txtLogo.setText(getString(R.string.phienban)+" "+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.welcome_transition);
        txtLogo.setAnimation(myanim);
        img.setAnimation(myanim);
        final Intent i=new Intent(this,LoginActivity.class);//layout tiep theo
        Thread timer=new Thread(){
            public void  run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
