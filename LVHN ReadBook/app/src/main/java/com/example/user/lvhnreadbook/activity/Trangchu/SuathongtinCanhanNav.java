package com.example.user.lvhnreadbook.activity.Trangchu;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.lvhnreadbook.ultil.Constant;
import com.example.user.lvhnreadbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class SuathongtinCanhanNav extends AppCompatActivity {
    Toolbar toolbar;
    EditText tendanhnhap,mk,nhaplaimk,email;
    Button btn_luu;
    ImageView anhdaidien;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;

    private static final int PICK_FROM_FILE=2;

    private final int REQUEST_CAMERA=1;
    private Uri imageCaptureUri;
    private final int REQUEST_CODE=166554;
    boolean chonanh=false;
    boolean loadanh=false;
    FirebaseStorage storage= FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReference();
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constant.theme);
        setContentView(R.layout.nav_suathongtin_canhan);
        initView();
        actionbar();
        actionHinhanh();
    }
    public void initView(){
        firebaseAuth=firebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        toolbar=findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);
        email=findViewById(R.id.ed_email);
        tendanhnhap=findViewById(R.id.ed_tentk);
        mk=findViewById(R.id.ed_nhapmk);
        nhaplaimk=findViewById(R.id.ed_nhaplaimmk);
        btn_luu=findViewById(R.id.btn_luu);
        anhdaidien=findViewById(R.id.img_anhdaidien);
        loaddata();
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedata();


        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public  void updatedata(){

        DatabaseReference databaseuptenanh=FirebaseDatabase.getInstance().getReference().child("thanhviens").child(user.getUid());
            if(chonanh==true){
                String path="thanhvien/";
                Calendar calendar=Calendar.getInstance();
                path=path+tendanhnhap.getText().toString()+calendar.getTimeInMillis()+".jpg";
                StorageReference mountainsRef=storageRef.child(path);
                anhdaidien.setDrawingCacheEnabled(true);
                anhdaidien.buildDrawingCache();
                Bitmap bitmap = anhdaidien.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(SuathongtinCanhanNav.this,"Lỗi up file",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(SuathongtinCanhanNav.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        Log.d("AAA",downloadUrl+"");
                    }
                });

                databaseuptenanh.child("anhdaidien").setValue(path);
            }else{
                if(loadanh==true){
                    String path="thanhvien/";

                    Calendar calendar=Calendar.getInstance();
                    path=path+tendanhnhap.getText().toString()+calendar.getTimeInMillis()+".jpg";

                    StorageReference riversRef=storageRef.child(path);
                    UploadTask uploadTask = riversRef.putFile(imageCaptureUri);

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(SuathongtinCanhanNav.this,"Lỗi up file",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(SuathongtinCanhanNav.this,"Up thanh công",Toast.LENGTH_SHORT).show();
                        }
                    });
                    databaseuptenanh.child("anhdaidien").setValue(path);
                }else{
                    //giữ nguyên
                }
            }
        final String tendn=tendanhnhap.getText().toString();
        String pass=mk.getText().toString().trim();
        String emaill=email.getText().toString().trim();
        String pass2=nhaplaimk.getText().toString().trim();
        if(tendn.trim().length()==0){
            Toast.makeText(SuathongtinCanhanNav.this,R.string.tentkerr,Toast.LENGTH_SHORT).show();
        }else {if(emaill.length()==0){
            Toast.makeText(SuathongtinCanhanNav.this,R.string.emailerr,Toast.LENGTH_SHORT).show();
        }else{  if (pass.trim().length() == 0) {
                Toast.makeText(SuathongtinCanhanNav.this, R.string.mkerr, Toast.LENGTH_SHORT).show();
            } else {
                if (!pass2.trim().equals(pass.trim())) {
                    Toast.makeText(SuathongtinCanhanNav.this, R.string.nhaplaimmkerr, Toast.LENGTH_SHORT).show();
                }
                else {

                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(tendn)
                            .setPhotoUri(Uri.EMPTY)
                            .build();
                    user.updateEmail(emaill);
                    user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("thanhviens").child(user.getUid());
                            databaseReference.child("ten").setValue(tendn);
                            Toast.makeText(SuathongtinCanhanNav.this, R.string.updatethanhcong, Toast.LENGTH_SHORT).show();
                        }

                    });
                    user.updatePassword(pass);

                }
        }


            }
        }
    }
        });

        }
    public  void loaddata(){
        if(user!=null) {
            String tentk = user.getDisplayName();
            String emailu=user.getEmail();
            tendanhnhap.setText(tentk);
            email.setText(emailu);
            final DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("thanhviens").child(user.getUid()).child("anhdaidien");

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  String  anh= dataSnapshot.getValue().toString();
                    Toast.makeText(SuathongtinCanhanNav.this,anh,Toast.LENGTH_SHORT).show();
                    StorageReference storagehinhanh = FirebaseStorage.getInstance().getReference().child(anh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            anhdaidien.setImageBitmap(bitmap);
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }else{
        }

    }
    public void actionbar(){
        toolbar.setTitle("Thay đổi tài khoản");
        toolbar.setBackgroundColor(Constant.theme);
        toolbar.setBackgroundColor(Constant.color);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SuathongtinCanhanNav.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void actionHinhanh(){
        final String[] items=new String[] {"Từ Camera","Từ thiết bị"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Từ Camera")){
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent,REQUEST_CAMERA);
                    }
                }else{
                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Complete action using"),PICK_FROM_FILE);
                    Toast.makeText(SuathongtinCanhanNav.this,"chọn từ thư mục",Toast.LENGTH_SHORT).show();

                }
            }
        });
        final AlertDialog dialog=builder.create();
        anhdaidien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!= Activity.RESULT_OK)
            return;

        Bitmap bitmap=null;
        if(requestCode==PICK_FROM_FILE&& resultCode == RESULT_OK){
            Toast.makeText(SuathongtinCanhanNav.this,"chọn từ thư mục2",Toast.LENGTH_SHORT).show();
            imageCaptureUri=data.getData();
            anhdaidien.setImageURI(imageCaptureUri);
            loadanh=true;
            chonanh=false;

        }
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            anhdaidien.setImageBitmap(imageBitmap);
            chonanh=true;
            loadanh=false;
        }



    }

}
