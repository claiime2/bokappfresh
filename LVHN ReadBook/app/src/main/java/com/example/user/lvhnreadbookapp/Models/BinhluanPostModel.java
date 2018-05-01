package com.example.user.lvhnreadbookapp.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BinhluanPostModel implements Parcelable{
    ThanhvienModel thanhvienModel;
    String mauser;
    String mablp;
    String noidung;
    String ngay;



    private DatabaseReference datablPost;

    public DatabaseReference getDatablPost() {
        return datablPost;
    }

    public void setDatablPost(DatabaseReference datablPost) {
        this.datablPost = datablPost;
    }

    public BinhluanPostModel() {;
    datablPost= FirebaseDatabase.getInstance().getReference();
    }


    protected BinhluanPostModel(Parcel in) {
        thanhvienModel = in.readParcelable(ThanhvienModel.class.getClassLoader());
        mauser = in.readString();
        mablp = in.readString();
        noidung = in.readString();
        ngay = in.readString();
    }

    public static final Creator<BinhluanPostModel> CREATOR = new Creator<BinhluanPostModel>() {
        @Override
        public BinhluanPostModel createFromParcel(Parcel in) {
            return new BinhluanPostModel(in);
        }

        @Override
        public BinhluanPostModel[] newArray(int size) {
            return new BinhluanPostModel[size];
        }
    };

    public ThanhvienModel getThanhvienModel() {
        return thanhvienModel;
    }

    public void setThanhvienModel(ThanhvienModel thanhvienModel) {
        this.thanhvienModel = thanhvienModel;
    }

    public String getMablp() {
        return mablp;
    }

    public void setMablp(String mablp) {
        this.mablp = mablp;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public BinhluanPostModel(ThanhvienModel thanhvienModel,String noidung, String ngay,String mablp) {
        this.thanhvienModel = thanhvienModel;
        this.noidung = noidung;
        this.ngay = ngay;
        this.mablp=mablp;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(thanhvienModel, flags);
        dest.writeString(mauser);
        dest.writeString(mablp);
        dest.writeString(noidung);
        dest.writeString(ngay);
    }

}
