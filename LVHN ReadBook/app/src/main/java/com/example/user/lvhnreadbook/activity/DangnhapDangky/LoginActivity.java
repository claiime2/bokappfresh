package com.example.user.lvhnreadbook.activity.DangnhapDangky;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.lvhnreadbook.activity.Trangchu.MainActivity;
import com.example.user.lvhnreadbook.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Share;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener, FirebaseAuth.AuthStateListener{
    Button buttonDangky,btnDangnhap;
    Button buttonBoqua,dangnhapFace;
    TextView quenmk;
    boolean savelogin;
    EditText edtenTk,edMK;
    CheckBox rememberPass;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    CallbackManager mCallbackFacebook;
    LoginManager loginManager;
    DatabaseReference themuser;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int KIEMTRA_PROVIDER_DANGNHAP=0;
    private int dangnhapface=0;

    List<String> permisionFacebook= Arrays.asList("email","public_profile");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.isInitialized();
        firebaseAuth=FirebaseAuth.getInstance();
        loginManager=LoginManager.getInstance();
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        setContentView(R.layout.activity_login);
        initview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    public void initview(){
        mCallbackFacebook=CallbackManager.Factory.create();
        //firebase
        sharedPreferences=getSharedPreferences("loginref",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //view
        quenmk=findViewById(R.id.text_quenmk);
        buttonBoqua=findViewById(R.id.button_boquadangnhap);
        buttonDangky=findViewById(R.id.button_dangky);
        btnDangnhap=findViewById(R.id.btn_dangnhap);
        rememberPass=findViewById(R.id.checbox_nhommk);
        edMK=findViewById(R.id.ed_matkhauDN);
        edtenTk=findViewById(R.id.ed_emailDN);
        rememberPass=findViewById(R.id.checbox_nhommk);

        //dang nhap face
        dangnhapFace=findViewById(R.id.btn_damgnhapface);
        progressDialog=new ProgressDialog(this);
        btnDangnhap.setOnClickListener(this);
        dangnhapFace.setOnClickListener(this);
        quenmk.setOnClickListener(this);
        buttonBoqua.setOnClickListener(this);
        buttonDangky.setOnClickListener(this);
        firebaseAuth.addAuthStateListener(this);
        savelogin=sharedPreferences.getBoolean("savelogin",true);
        if(savelogin==true){
            edtenTk.setText(sharedPreferences.getString("username",null));
            edMK.setText(sharedPreferences.getString("password",null));

        }else{
            edtenTk.setText(null);
            edMK.setText(null);
        }



    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.button_dangky:
                Intent dangky=new Intent(LoginActivity.this,DangkyActivity.class);
                startActivity(dangky);
                break;
            case R.id.button_boquadangnhap:
                Intent boquadangnhap=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(boquadangnhap);
                break;
            case R.id.text_quenmk:
                Intent guimail=new Intent(LoginActivity.this,ForgetPassActivity.class);
                startActivity(guimail);
                break;
            case R.id.btn_dangnhap:

                Dangnhap();
                break;
            case R.id.btn_damgnhapface:
                dangnhapface=1;
                dangnhapFacebook();
                break;
        }

    }
    private void Dangnhap(){
        final String email=edtenTk.getText().toString().trim();
        final String pass=edMK.getText().toString().trim();
        progressDialog.setMessage(getString(R.string.dangxuli));
        progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_dialog));

        if(email.trim().length()==0){
            Toast.makeText(LoginActivity.this,R.string.emailerr,Toast.LENGTH_SHORT).show();

        }else { if(!Kiemtraemail(email)){
            Toast.makeText(LoginActivity.this, R.string.emailerorr, Toast.LENGTH_SHORT).show();
        }else{ if (pass.trim().length()==0){
            Toast.makeText(LoginActivity.this, R.string.mkerr, Toast.LENGTH_SHORT).show();
        }else{
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.dnthatbai, Toast.LENGTH_SHORT).show();


                    } else {
                        if(rememberPass.isChecked()){
                            editor.putBoolean("savelogin",true);
                            editor.putString("username",email);
                            editor.putString("password",pass);
                            editor.commit();
                        }else{
                            editor.putBoolean("savelogin",false);
                            editor.putString("username",null);
                            editor.putString("password",null);
                            editor.commit();
                        }
                        progressDialog.dismiss();

                    }
                }
            });

        }

         }

        }
    }
    private boolean Kiemtraemail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void ChungthucdangnhapFirebase(String tokenID){
        if(KIEMTRA_PROVIDER_DANGNHAP==2){//nguoi dung dang dang nhap facebook
            AuthCredential authCredential= FacebookAuthProvider.getCredential(tokenID);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }
    private void dangnhapFacebook(){
        loginManager.logInWithReadPermissions(this,permisionFacebook);
        loginManager.registerCallback(mCallbackFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                KIEMTRA_PROVIDER_DANGNHAP=2;
                String tokenID=loginResult.getAccessToken().getToken();
                ChungthucdangnhapFirebase(tokenID);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackFacebook.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user!=null){//dang nhp thanh cong
            Toast.makeText(LoginActivity.this,R.string.dangnhapthanhcong, Toast.LENGTH_SHORT).show();
            if(dangnhapface==1) {
                themuser = FirebaseDatabase.getInstance().getReference().child("thanhviens").child(user.getUid());
                themuser.child("ten").setValue(user.getDisplayName());
                themuser.child("anhdaidien").setValue("thanhvien/default_user.jpg");
            }
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{


        }
    }
}
