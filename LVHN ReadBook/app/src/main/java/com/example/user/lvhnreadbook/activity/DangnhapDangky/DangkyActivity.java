package com.example.user.lvhnreadbook.activity.DangnhapDangky;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.lvhnreadbook.R;
import com.example.user.lvhnreadbook.activity.Trangchu.SuathongtinCanhanNav;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 16/03/2018.
 */

public class DangkyActivity extends AppCompatActivity implements View.OnClickListener{
    Button iconback,btnDangky;
    EditText edtenTk,edmatkhau,edemail,ednhaplaiMK;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseUser user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        intview();



    }
    public  void intview(){
        firebaseAuth=FirebaseAuth.getInstance();
        iconback=findViewById(R.id.icon_back);
        btnDangky=findViewById(R.id.btn_dangky);
        edtenTk=findViewById(R.id.ed_tentk);
        edmatkhau=findViewById(R.id.ed_nhapmk);
        ednhaplaiMK=findViewById(R.id.ed_nhaplaimmk);
        edemail=findViewById(R.id.ed_email);
        progressDialog=new ProgressDialog(this);


        iconback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backlogin=new Intent(DangkyActivity.this,LoginActivity.class);
                startActivity(backlogin);
            }
        });
        btnDangky.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage(getString(R.string.dangxuli));
        progressDialog.setIndeterminate(true);
        progressDialog.setIndeterminateDrawable(getDrawable(R.drawable.progressbar_dialog));

        String mail=edemail.getText().toString();
        String mk=edmatkhau.getText().toString();
        String nlmk=ednhaplaiMK.getText().toString();
        final String ten= edtenTk.getText().toString();


        if(ten.trim().length()==0){
            Toast.makeText(DangkyActivity.this,R.string.tentkerr,Toast.LENGTH_SHORT).show();
        }else if(mail.trim().length()==0) {
            Toast.makeText(DangkyActivity.this, R.string.emailerr, Toast.LENGTH_SHORT).show();
        }else {
            if (!Kiemtraemail(mail)) {
                Toast.makeText(DangkyActivity.this, R.string.emailerorr, Toast.LENGTH_SHORT).show();
            }
            if (mk.trim().length() == 0) {
                Toast.makeText(DangkyActivity.this, R.string.mkerr, Toast.LENGTH_SHORT).show();
            } else if (!nlmk.equals(mk)) {
                Toast.makeText(DangkyActivity.this, R.string.nhaplaimmkerr, Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(mail, mk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            user=firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(ten)
                                    .build();

                            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(DangkyActivity.this, user.getDisplayName() , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            String id = user.getUid();
                            Toast.makeText(DangkyActivity.this, id , Toast.LENGTH_SHORT).show();
                            //For adding into database
                            databaseReference = databaseReference.child("thanhviens").child(id);
                            databaseReference.child("ten").setValue(ten);
                            databaseReference.child("anhdaidien").setValue("thanhvien/default_user.jpg");
                           // Toast.makeText(DangkyActivity.this, R.string.dangkythanhcong, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DangkyActivity.this, R.string.dangkyfail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    private boolean Kiemtraemail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
