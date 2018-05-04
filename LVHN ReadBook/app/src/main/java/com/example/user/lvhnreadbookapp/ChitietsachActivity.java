package com.example.user.lvhnreadbookapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.ultil.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by User on 25/03/2018.
 */

public class ChitietsachActivity extends AppCompatActivity {
    static ImageButton btDocSach;
    static String masach;
    static String matheloai;
    static ImageView ivAnhBia;
    static TextView tvTenSach;
    static TextView tvTacGia;
    static TextView tvLuotXem;
    static RatingBar rbDanhGia;
    static TextView tvLuotThich;
    static TextView tvNgayDang;
    static TextView tvNamXuatBan;
    static TextView tvMoTa;
    static ListView lvbinhluan;
    public static int luotxem=0;
    public static String mas,tens,tacgia,hinh,file, linkhinh, linkfile;
    static DatabaseReference myRef;
    static DatabaseReference database;
    public static SQLiteHelper db;
    static ArrayList<SachModel> data;
    static SachModel sachModel=new SachModel();

    //file json
    SQLiteJson json;

    boolean flag=false;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsach);
        setTheme(Constant.theme);
        json=SQLiteJson.getHelper(this);
        db=SQLiteHelper.getHeler(this);
        data=new ArrayList<>();
        db.loadData(data);
        Log.d("sizedata", String.valueOf(data.size()));
        for(int i=0;i<data.size();i++){
            Log.d("data", data.get(i).getMasach().toString());
        }
        Log.d("test","ok");
        setControl();

        masach=getIntent().getStringExtra("keysach");
        matheloai=getIntent().getStringExtra("keytheloai");
        myRef= FirebaseDatabase.getInstance().getReference();
        myRef=myRef.child("sachs").child(matheloai).child(masach);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sachModel=dataSnapshot.getValue(SachModel.class);
                mas=dataSnapshot.getKey();
                tens=dataSnapshot.child("tensach").getValue().toString();
                tacgia=dataSnapshot.child("tacgia").getValue().toString();
                hinh=dataSnapshot.child("hinhanh").getValue().toString();
                file=dataSnapshot.child("file").getValue().toString();
                luotxem=Integer.parseInt(dataSnapshot.child("luotxem").getValue().toString());
                Log.d("test",mas);
                Log.d("file",file);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference islandRef =storageRef.child(sachModel.getHinhanh());
                final long ONE_MEGABYTE = 1024 * 1024;
                islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ivAnhBia.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

                tvTenSach.setText(sachModel.getTensach());
                tvTacGia.setText(sachModel.getTacgia());
                tvLuotXem.setText(String.valueOf(sachModel.getLuotxem()));
                tvLuotThich.setText(String.valueOf(sachModel.getLuotthich()));
                tvMoTa.setText(sachModel.getGioithieu());
                tvNamXuatBan.setText(String.valueOf(sachModel.getNamxb()));
                tvNgayDang.setText(sachModel.getNgaydang());
                tvNgayDang.setText(String.valueOf(sachModel.getNgaydang()));
                rbDanhGia.setRating(Float.parseFloat(String.valueOf(sachModel.getDanhgia())));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rbDanhGia.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                myRef.child("danhgia").setValue(rating);
            }
        });
        btDocSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test",masach);
                if(data.size()>0){
                    for (int i=0;i<data.size();i++){
                        Log.d("da",data.get(i).getMasach());
                        if(data.get(i).getMasach().equalsIgnoreCase(masach)){
                            Log.d("dk","ok");
                            flag=true;
                            Intent intent=new Intent(ChitietsachActivity.this,DocsachActivity.class);
                            intent.putExtra("path",data.get(i).getFile());
                            startActivity(intent);
                            break;
                        }
                    }
                }
                Log.d("flag",String.valueOf(flag));
                if(flag==false){
                    luotxem++;
                    myRef.child("luotxem").setValue(luotxem);
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    Log.d("hinh",hinh);
                    StorageReference pathReference = storageRef.child(hinh);
                    String[] split=hinh.split("/");
                    final File localHinh= new File(getCacheDir(),split[1]);
                    linkhinh=localHinh.toString();
                    pathReference.getFile(localHinh).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            linkhinh=localHinh.toString();
                            Log.e("firebase ",";local tem file created  created " +localHinh.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("firebase ",";local tem file not created  created " +exception.toString()+localHinh.toString());
                        }
                    });
                    pathReference=storageRef.child(file);
                    String[] split2=file.split("/");
                    final File localFile= new File(getCacheDir(),split2[1]);
                    linkfile=localFile.toString();
                    pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            linkfile=localFile.toString();
                            Log.e("firebase ",";local tem file created  created " +localFile.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("firebase ",";local tem file not created  created " +exception.toString()+localFile.toString());
                        }
                    });
                    SachModel sachModel1=new SachModel();
                    sachModel1.setMasach(mas);
                    sachModel1.setTensach(tens);
                    sachModel1.setTacgia(tacgia);
                    sachModel1.setHinhanh(linkhinh);
                    Log.d("linkhinh",linkhinh);
                    sachModel1.setFile(linkfile);
                    Log.d("file",linkfile);
                    db.insert(sachModel1);
                    //json
                    String[] s=sachModel1.getFile().toString().split("/");
                    json.insert(s[s.length-1],"_simple_book","id-idp140724860948704",-1, "OEBPS/ch01.xhtml",false,"0");


                    Log.d("data", mas);
                    Intent intent=new Intent(ChitietsachActivity.this,DocsachActivity.class);
                    intent.putExtra("path",linkfile.toString());
                    startActivity(intent);


                }


            }
        });

    }
    public void setControl(){
        ivAnhBia=(ImageView) findViewById(R.id.ivAnhBia);
        tvTenSach=(TextView) findViewById(R.id.tvTenSach);
        tvTacGia=(TextView) findViewById(R.id.tvTacGia);
        tvMoTa=(TextView) findViewById(R.id.tvGioiThieu);
        tvLuotXem=(TextView) findViewById(R.id.tvLuotXem);
        rbDanhGia=(RatingBar) findViewById(R.id.rbSach);
        tvLuotThich=(TextView) findViewById(R.id.tvLuotThich);
        tvNamXuatBan=(TextView) findViewById(R.id.tvNamXuatBan);
        tvNgayDang=(TextView) findViewById(R.id.tvNgayDang);
        btDocSach=(ImageButton) findViewById(R.id.btDocSach);

    }
}
