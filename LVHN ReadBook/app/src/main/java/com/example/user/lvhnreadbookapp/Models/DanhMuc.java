package com.example.user.lvhnreadbookapp.Models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DanhMuc {
    String matheloai;
    String ten;
    String anhtheloai;
    private DatabaseReference dataTheloai;


    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnhtheloai() {
        return anhtheloai;
    }

    public void setAnhtheloai(String anhtheloai) {
        this.anhtheloai = anhtheloai;
    }

    public DatabaseReference getDataTheloai() {
        return dataTheloai;
    }

    public void setDataTheloai(DatabaseReference dataTheloai) {
        this.dataTheloai = dataTheloai;
    }

    public DanhMuc(){
        dataTheloai= FirebaseDatabase.getInstance().getReference();
    }
    public void getdataTheloai(final DanhmucInterface danhmucInterface){
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotTheloai=dataSnapshot.child("theloais");

                for (DataSnapshot valueTL:dataSnapshotTheloai.getChildren()){
                    DanhMuc danhMuc=new DanhMuc();
                    danhMuc=valueTL.getValue(DanhMuc.class);
                    danhMuc.matheloai=valueTL.getKey();
                    //Log.d("kiemtratheloai",danhMuc.getTen()+"");
                    Log.d("matheloai",danhMuc.getMatheloai()+" ");
                    Log.d("kiemtratheloai",danhMuc.getAnhtheloai()+"");
                    danhmucInterface.getDanhmuc(danhMuc);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dataTheloai.addListenerForSingleValueEvent(valueEventListener);

    }
}
