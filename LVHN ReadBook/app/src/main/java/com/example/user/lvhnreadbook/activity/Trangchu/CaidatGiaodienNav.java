package com.example.user.lvhnreadbook.activity.Trangchu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.user.lvhnreadbook.ultil.ColorListener;
import com.example.user.lvhnreadbook.ultil.Constant;
import com.example.user.lvhnreadbook.R;

import java.util.ArrayList;
import java.util.List;

public class CaidatGiaodienNav extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button button;
    //
    private ImageButton one;
    private ImageButton two;
    private ImageButton three;
    private ImageButton four;
    private ImageButton five;
    private ImageButton six;
    private ImageButton seven;
    private ImageButton eight;
    //
    private List<Integer> colors;
    private List<ImageButton> buttons;

    private ColorListener myColorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setTheme(Constant.theme);
        setContentView(R.layout.nav_caidat_giaodien);
        initView();
        actionbar();
        settingcolortheme();
    }
    public void initView(){
        toolbar=findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences=getSharedPreferences("Setting",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //
        one =      (ImageButton)findViewById(R.id.b1);
        two =      (ImageButton)findViewById(R.id.b2);
        three =    (ImageButton)findViewById(R.id.b3);
        four =     (ImageButton)findViewById(R.id.b4);
        five =     (ImageButton)findViewById(R.id.b5);
        six =      (ImageButton)findViewById(R.id.b6);
        seven =    (ImageButton)findViewById(R.id.b7);
        eight =    (ImageButton)findViewById(R.id.b8);
    }
    public void actionbar(){
        toolbar.setTitle("Chọn màu ứng dụng");
        toolbar.setBackgroundColor(Constant.color);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(CaidatGiaodienNav.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void changecolorApp(){
        switch (Constant.color){
            case 0xff183545:
                Constant.theme = R.style.AppTheme_darkblue;
                break;
            case  0xffd93f3f:
                Constant.theme = R.style.AppTheme_red;//
                break;
            case 0xfb518c50:
                Constant.theme = R.style.AppTheme_Greendeep;//
                break;
            case 0xffe79c24:
                Constant.theme = R.style.AppTheme_orange;//
                break;
            case  0xff7f39a4:
                Constant.theme = R.style.AppTheme_purpal;
                break;
            case 0xff8e4848:
                Constant.theme = R.style.AppTheme_brown;
                break;
            case 0xff09c6df:
                Constant.theme = R.style.AppTheme_skyblue;
                break;
            case 0xff3ac43e:
                Constant.theme = R.style.AppTheme_green;
                break;

            default:
                Constant.theme = R.style.AppTheme_darkblue;
                break;
        }
    }

     public void settingcolortheme(){
        colors = new ArrayList<>();
        colors.add(darkblue);
        colors.add(purple);
        colors.add(green);
        colors.add(orange);
        colors.add(brown);
        colors.add(skyblue);
        colors.add(greendeep);
        colors.add(red);

        buttons = new ArrayList<>();
        buttons.add(one);
        buttons.add(two);
        buttons.add(three);
        buttons.add(four);
        buttons.add(five);
        buttons.add(six);
        buttons.add(seven);
        buttons.add(eight);
        Colorize();// chỉnh tro2nn và hiển thị màu cho button
        setListeners();//button lang nghe
         this.setColorListener(new ColorListener() {
             @Override
             public void OnColorClick(View v, int color) {
                 Constant.color=color;
                 changecolorApp();
                 editor.putInt("color",color);//gán color vs màu
                 editor.putInt("theme",Constant.theme);
                 editor.commit();
                 Intent intent =new Intent(CaidatGiaodienNav.this,CaidatGiaodienNav.class);
                 startActivity(intent);

             }
         });
    }



    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(myColorListener != null)
                myColorListener.OnColorClick(v, (int)v.getTag());
        }
    };
    private void setListeners() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setTag(colors.get(i));
            buttons.get(i).setOnClickListener(listener);
        }
    }
    public void setColorListener(ColorListener listener){
        this.myColorListener = listener;
    }

    private void Colorize() {
        for (int i = 0; i < buttons.size(); i++) {
            ShapeDrawable d = new ShapeDrawable(new OvalShape());
            d.setBounds(58, 58, 58, 58);
            Log.e("Shape drown no", i + "");
            buttons.get(i).setVisibility(View.VISIBLE);

            d.getPaint().setStyle(Paint.Style.FILL);
            d.getPaint().setColor(colors.get(i));

            buttons.get(i).setBackground(d);
        }
    }
    //CONSTANTS
    public final int red =        0xffd93f3f;
    public final int purple =     0xff7f39a4;
    public final int green =     0xff3ac43e;
    public final int skyblue =       0xff09c6df;
    public final int darkblue =  0xff183545;
    public final int brown =      0xff8e4848;
    public final int orange = 0xffe79c24;
    public final int greendeep = 0xfb518c50;
    public final int White =      0xffffffff;

}
