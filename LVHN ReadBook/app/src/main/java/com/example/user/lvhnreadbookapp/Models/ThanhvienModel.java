package com.example.user.lvhnreadbookapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThanhvienModel implements Parcelable{
    String ten;
    String maso;
    String anhdaidien;
    private DatabaseReference datanoteThanhvien;

    public ThanhvienModel() {
        datanoteThanhvien= FirebaseDatabase.getInstance().getReference();
    }

    public ThanhvienModel(String ten, String anhdaidien) {
        this.ten = ten;
        this.anhdaidien = anhdaidien;
    }

    protected ThanhvienModel(Parcel in) {
        ten = in.readString();
        maso = in.readString();
        anhdaidien = in.readString();
    }

    public static final Creator<ThanhvienModel> CREATOR = new Creator<ThanhvienModel>() {
        @Override
        public ThanhvienModel createFromParcel(Parcel in) {
            return new ThanhvienModel(in);
        }

        @Override
        public ThanhvienModel[] newArray(int size) {
            return new ThanhvienModel[size];
        }
    };

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getAnhdaidien() {
        return anhdaidien;
    }

    public void setAnhdaidien(String anhdaidien) {
        this.anhdaidien = anhdaidien;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ten);
        dest.writeString(maso);
        dest.writeString(anhdaidien);
    }
}
