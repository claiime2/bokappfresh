package com.example.user.lvhnreadbookapp.activity.DangnhapDangky;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.activity.Trangchu.MainActivity;
import com.example.user.lvhnreadbookapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity{
    Button trove;
    Button guimail,dangkytk,boqua;
    EditText editemail;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initview();



    }
    public void initview(){
        trove= this.findViewById(R.id.icon_returnback);
        editemail=findViewById(R.id.ed_maildk);
        guimail=this.findViewById(R.id.btn_khoiphucmk);
        firebaseAuth=FirebaseAuth.getInstance();
        dangkytk=findViewById(R.id.button_dangky);
        boqua=findViewById(R.id.button_boquadangnhap);
        alertDialog=new AlertDialog.Builder(this);

        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(ForgetPassActivity.this,LoginActivity.class);
                startActivity(back);
            }
        });
        guimail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editemail.getText().toString();
                boolean kiemtraemail= Kiemtraemail(email);
                if(kiemtraemail){
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(ForgetPassActivity.this,R.string.xemmail,Toast.LENGTH_SHORT).show();

                                alertDialog.setTitle("Thông báo");
                                alertDialog.setIcon(R.mipmap.ic_launcher);
                                alertDialog.setMessage("Vui lòng kiểm tra email");
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getBaseContext(),"OK",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(ForgetPassActivity.this,R.string.dnthatbai,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(ForgetPassActivity.this,R.string.emailerorr,Toast.LENGTH_SHORT).show();
                }
            }
        });
        boqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boquadangnhap=new Intent(ForgetPassActivity.this,MainActivity.class);
                startActivity(boquadangnhap);
            }
        });
        dangkytk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dangky=new Intent(ForgetPassActivity.this,DangkyActivity.class);
                startActivity(dangky);
            }
        });

    }
    private boolean Kiemtraemail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
