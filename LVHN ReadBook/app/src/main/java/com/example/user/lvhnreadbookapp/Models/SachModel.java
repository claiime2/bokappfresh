package com.example.user.lvhnreadbookapp.Models;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SachModel {
    String masach,matheloai,file,tensach,ngaydang,tacgia,gioithieu,hinhanh;
    long danhgia,luotxem,luotthich,namxb;
    private DatabaseReference dataSach;

    public SachModel() {
        dataSach= FirebaseDatabase.getInstance().getReference();
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getGioithieu() {
        return gioithieu;
    }

    public void setGioithieu(String gioithieu) {
        this.gioithieu = gioithieu;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public long getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(long danhgia) {
        this.danhgia = danhgia;
    }

    public long getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(long luotxem) {
        this.luotxem = luotxem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public long getNamxb() {
        return namxb;
    }

    public void setNamxb(long namxb) {
        this.namxb = namxb;
    }

    public DatabaseReference getDataSach() {
        return dataSach;
    }

    public void setDataSach(DatabaseReference dataSach) {
        this.dataSach = dataSach;
    }

    public void getdataSach(final SachInterface sachInterface){
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotSach=dataSnapshot.child("sachs");
                for (DataSnapshot valueSach:dataSnapshotSach.getChildren()){//cac the loai
                    String theloai=valueSach.getKey();//matheloai
                   // Log.d("checksach",theloai);
                    for(DataSnapshot value:valueSach.getChildren()){
                            SachModel sachModel=value.getValue(SachModel.class);
                            sachModel.setMatheloai(theloai);
                            sachModel.setMasach(value.getKey());
                            //Log.d("checksach",value.getKey());
                            sachInterface.getSachModel(sachModel);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dataSach.addListenerForSingleValueEvent(valueEventListener);

    }
    public void getdatatheloai(final SachInterface sachInterface, final String theloaitim){
        final String tim=theloaitim;
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotSach=dataSnapshot.child("sachs");
                for (DataSnapshot valueSach:dataSnapshotSach.getChildren()){//cac the loai
                    String theloai=valueSach.getKey();//matheloai
                    Log.d("checksach",theloai);
                    TheloaiModel duyettheloai=dataSnapshot.child("theloais").child(theloai).getValue(TheloaiModel.class);
                    if(duyettheloai.getTen().equals(tim)){
                        for(DataSnapshot value:valueSach.getChildren()){
                            SachModel sachModel=value.getValue(SachModel.class);
                            sachModel.setMatheloai(theloai);
                            sachModel.setMasach(value.getKey());
                            Log.d("checksach",value.getKey());

                            sachInterface.getSachModel(sachModel);
                        }
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dataSach.addListenerForSingleValueEvent(valueEventListener);

    }



}
