package com.example.user.lvhnreadbook.activity.Trangchu;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import  android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.lvhnreadbook.activity.DangnhapDangky.LoginActivity;
import com.example.user.lvhnreadbook.ultil.BottomNavigationViewHelper;
import com.example.user.lvhnreadbook.ultil.Constant;
import com.example.user.lvhnreadbook.Models.SachModel;
import com.example.user.lvhnreadbook.R;
import com.example.user.lvhnreadbook.adapter.RecycleMainAdapter;
import com.example.user.lvhnreadbook.SearchActivity;
import com.example.user.lvhnreadbook.adapter.ViewPagerAdapter;
import com.example.user.lvhnreadbook.ultil.SettingTime;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle mTonggle;
    ViewPager viewPager;
    RecyclerView recyclerView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    LinearLayout sliderDots;
    private int dotCount;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ImageView[] dots;
    FragmentManager fragmentManager;
    Button timkiem;
    ProgressBar progressBar;
    //
    private static final int ACTIVITY_NUM=2;
    private Context mContext= MainActivity.this;
    //
    private ArrayList<Object> listDataRecycleview = new ArrayList<>();
    //
    ProgressDialog progressDialog;
    AlertDialog.Builder popDialog;
    //textuser header drawer
    TextView username;
    TextView email;
    ImageView anhuser;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(Constant.theme);
        setContentView(R.layout.activity_main);
        initView();
        //viewQuang cao
        ActionBar();
        ActionViewpager();
        setupBottomNavigationView();
        ActionRecycleview();
        ActionTimkiem();//lỗi

    }

    public void ActionViewpager(){
        final List<Integer> dem =new ArrayList<>();
        final List<String> nameAnh=new ArrayList<String>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("ad");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot adname : dataSnapshot.getChildren()) {
                    String name = adname.child("anh").getValue(String.class);
                    i++;
                    dem.add(i);
                    nameAnh.add(name);

                }
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, nameAnh);
                viewPager.setAdapter(viewPagerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dotCount=3;
        dots=new ImageView[dotCount];

        for(int i=0;i<dotCount;i++){
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<dotCount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(),4000,4000);
    }
    public void ActionBar(){
        //mTonggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        //drawerLayout.addDrawerListener(mTonggle);
        //mTonggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Constant.color);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   drawerLayout.openDrawer(GravityCompat.START);
                    //Toast.makeText(MainActivity.this, "oooooo", Toast.LENGTH_SHORT).show();

                    //onBackPressed();
                }
        });

        setupDrawerContent(navigationView);
    }


    public void selectItemDrawer(MenuItem menuItem){
        Intent intent;
        switch(menuItem.getItemId()){
            case R.id.thaydoi_canhan:
                if(user!=null) {
                    intent = new Intent(MainActivity.this, SuathongtinCanhanNav.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.caidat_giaodien:
                intent =new Intent(MainActivity.this,CaidatGiaodienNav.class);
                startActivity(intent);
                break;
            case R.id.ngonngu:
                Toast.makeText(MainActivity.this,"chưa hỗ trợ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.caidat_time:
                activityTimer();
                setTitle("ddddddd");
                break;
            case R.id.hotro_ungdung:
                intent =new Intent(MainActivity.this,HuongdanHotroNav.class);
                startActivity(intent);

                break;
            case R.id.gioithieu_lienhe:
                intent =new Intent(MainActivity.this,LienheGioithieuNav.class);
                startActivity(intent);

                break;
            case R.id.btn_dangxuat:
                if(user!=null){
                   firebaseAuth.signOut();
                    intent =new Intent(this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,"Bạn đã đăng xuất",Toast.LENGTH_SHORT).show();
                }else{
                    intent =new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.face_theodoi:

                Toast.makeText(MainActivity.this,"chưa hổ trợ",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this,"Sai",Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawers();
        menuItem.setChecked(false);
        if(menuItem.getItemId()==R.id.caidat_time) drawerLayout.openDrawer(Gravity.START);

    }
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void activityTimer(){
        popDialog=new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        final SeekBar seek;
        final TextView start;
        TextView end ;
        Button huy;
        final Button demgio;
        final TextView conlai;
        sharedPreferences=getSharedPreferences("settingtime",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.seekbar,(ViewGroup) findViewById(R.id.layout_seekbar));

        seek= Viewlayout.findViewById(R.id.item_seekbar);
        start=Viewlayout.findViewById(R.id.text_start);
        end=Viewlayout.findViewById(R.id.text_end);
        conlai=Viewlayout.findViewById(R.id.text_demnguoc);
        huy=Viewlayout.findViewById(R.id.btn_huy);
        demgio=Viewlayout.findViewById(R.id.btn_demgio);
        demgio.setEnabled(false);
        demgio.setTextColor(R.color.graylight);
        seek.setMax(120);
        end.setText("120 phút ");
        start.setText("0 phút");
        conlai.setText("");
        huy.setText("Hủy");
        demgio.setText("Bắt đầu đếm giờ");
        popDialog.setTitle("Hẹn giờ kết thúc đọc");
        popDialog.setView(Viewlayout);
        seek.getProgressDrawable().setColorFilter(R.color.colorTrangchu, PorterDuff.Mode.SRC_IN);
        seek.getThumb().setColorFilter(R.color.colorTrangchu, PorterDuff.Mode.SRC_IN);
        demgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "đang xử lí ", Toast.LENGTH_SHORT).show();
                final int max=seek.getProgress()*60000;
                CountDownTimer countDownTimer=new CountDownTimer(max,60000) {
                   int dem=max;
                    Menu menu = navigationView.getMenu();
                    MenuItem navTime = menu.findItem(R.id.caidat_time);
                     @Override
                    public void onTick(long millisUntilFinished)
                    {
                        if(seek.getProgress()>=0) {
                            SettingTime.demgio=dem;
                            conlai.setText("Sau " + SettingTime.demgio/60000+ " phút ứng dụng sẽ báo");
                            TextView s = new TextView(MainActivity.this);
                            seek.setProgress(dem / 60000);
                            Toast.makeText(MainActivity.this, SettingTime.demgio/60000 + "", Toast.LENGTH_SHORT).show();

                            navTime.setTitle("Hẹn giờ kết thúc đọc       "  +SettingTime.demgio/60000+" phút");
                            dem = dem - 60000;
                        }
                    }
                    @Override
                    public void onFinish() {
                        navTime.setTitle("Hẹn giờ kết thúc đọc");
                        Toast.makeText(MainActivity.this, "hết giờ ", Toast.LENGTH_SHORT).show();
                    }
                };
              countDownTimer.start();
            }
        });
        final AlertDialog alertDialog =popDialog.create();
          huy.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  alertDialog.dismiss();
              }
          });

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                start.setText(""+ progress+" phút");
                if(seek.getProgress()==0){
                    demgio.setEnabled(false);
                    demgio.setTextColor(R.color.graylight);
                }
                if(seek.getProgress()>0){
                    demgio.setEnabled(true);
                    demgio.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        popDialog.create();
        popDialog.show();

    }



    public void initView(){
        toolbar= (Toolbar) findViewById(R.id.toolbar_trangchu);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        progressBar=findViewById(R.id.progressbar_trangchu);
        navigationView=(NavigationView) findViewById(R.id.navigationview_trangchu);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout_trangchu);
        sliderDots=(LinearLayout) findViewById(R.id.SliderDots);
        timkiem=findViewById(R.id.button_timkiem);
        fragmentManager=getSupportFragmentManager();
        progressDialog=new ProgressDialog(MainActivity.this);
        View headerNavi=navigationView.getHeaderView(0);

        username=headerNavi.findViewById(R.id.text_user);
        email=headerNavi.findViewById(R.id.emailuser);

        anhuser=headerNavi.findViewById(R.id.anhuser);
        firebaseAuth=FirebaseAuth.getInstance();
         user=firebaseAuth.getCurrentUser();
        Menu menu = navigationView.getMenu();
        MenuItem navTime = menu.findItem(R.id.thaydoi_canhan);
        MenuItem dangxuat=menu.findItem(R.id.btn_dangxuat);
        if(user!=null){
        String ten=user.getDisplayName();
            String emailuser=user.getEmail().toString();
           username.setText(ten);
            email.setText(emailuser);

            navTime.setTitle("Sửa thông tin cá nhân");
            dangxuat.setTitle("Đăng xuất");
            DatabaseReference databaseup= FirebaseDatabase.getInstance().getReference().child("thanhviens").child(user.getUid()).child("anhdaidien");
            String anh="thanhvien/user_default.jpg";
            databaseup.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String anh="thanhvien/user_default.jpg";
                    anh=dataSnapshot.getValue(String.class);
                    if(anh==null){
                        anh="thanhvien/user_default.jpg";
                    }
                    StorageReference storagehinhanh = FirebaseStorage.getInstance().getReference().child(anh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            anhuser.setImageBitmap(bitmap);
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{
            navTime.setVisible(false);
            dangxuat.setTitle("Đăng nhập");
        }

    }
    public class MyTimeTask extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);//tieptheo

                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    public void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(R.id.bottom_Na);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(MainActivity.this,this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    //
    private ArrayList<Object> getObject() {
        listDataRecycleview.add(SachModel.class);
        listDataRecycleview.add(SachModel.class);
        listDataRecycleview.add(SachModel.class);
        listDataRecycleview.add(SachModel.class);
        listDataRecycleview.add(SachModel.class);
        return  listDataRecycleview;
    }

    public void ActionRecycleview(){
        RecycleMainAdapter adapter = new RecycleMainAdapter(this, getObject());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.GONE);
    }
    public void ActionTimkiem(){
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timkiem=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(timkiem);
            }
        });
    }
}
