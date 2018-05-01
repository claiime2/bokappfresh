package com.example.user.lvhnreadbookapp.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.activity.ForumPost.BinhluanPostActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BaiPostModel implements Parcelable{
    String tieude;
    String mabaipost;
    String ngaytao;
    String mauser;
    long sotraloi;
    String noidungpost;
    ThanhvienModel thanhvienModel;
    List<BinhluanPostModel> listBL;
    private DatabaseReference dataRoot;


    public BaiPostModel(String tieude, String ngaytao, long sotraloi) {
        this.tieude = tieude;
        this.ngaytao = ngaytao;
        this.sotraloi = sotraloi;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public BaiPostModel() {
        dataRoot=FirebaseDatabase.getInstance().getReference();
    }

    public ThanhvienModel getThanhvienModel() {
        return thanhvienModel;
    }

    public void setThanhvienModel(ThanhvienModel thanhvienModel) {
        this.thanhvienModel = thanhvienModel;
    }

    public List<BinhluanPostModel> getListBL() {
        return listBL;
    }

    public void setListBL(List<BinhluanPostModel> listBL) {
        this.listBL = listBL;
    }

    public DatabaseReference getDataRoot() {
        return dataRoot;
    }

    public void setDataRoot(DatabaseReference dataRoot) {
        this.dataRoot = dataRoot;
    }

    protected BaiPostModel(Parcel in) {
        tieude = in.readString();
        mabaipost = in.readString();
        ngaytao = in.readString();
        mauser=in.readString();
        sotraloi = in.readLong();
        noidungpost = in.readString();
        listBL=new ArrayList<BinhluanPostModel>();
        in.readTypedList(this.listBL,BinhluanPostModel.CREATOR);

        thanhvienModel = in.readParcelable(ThanhvienModel.class.getClassLoader());
    }

    public static final Creator<BaiPostModel> CREATOR = new Creator<BaiPostModel>() {
        @Override
        public BaiPostModel createFromParcel(Parcel in) {
            return new BaiPostModel(in);
        }

        @Override
        public BaiPostModel[] newArray(int size) {
            return new BaiPostModel[size];
        }
    };

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }


    public String getMabaipost() {
        return mabaipost;
    }

    public void setMabaipost(String mabaipost) {
        this.mabaipost = mabaipost;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public long getSotraloi() {
        return sotraloi;
    }

    public void setSotraloi(long sotraloi) {
        this.sotraloi = sotraloi;
    }


    public String getNoidungpost() {
        return noidungpost;
    }

    public void setNoidungpost(String noidungpost) {
        this.noidungpost = noidungpost;
    }
    public void adddataPost(){
        dataRoot= FirebaseDatabase.getInstance().getReference().child("baiposts").push();
        dataRoot.child("tieude").setValue(tieude);
        dataRoot.child("ngaytao").setValue(ngaytao);
        dataRoot.child("sotraloi").setValue(0);
        dataRoot.child("noidungpost").setValue(noidungpost);
        //up anh
    }
    public void getdataPost(final BaiPostInterface baipostInterface){
        listBL=new ArrayList<>();
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotSach=dataSnapshot.child("baiposts");

                for (DataSnapshot valueSach:dataSnapshotSach.getChildren()){
                    BaiPostModel baiPostModel=new BaiPostModel();
                    baiPostModel=valueSach.getValue(BaiPostModel.class);
                    baiPostModel.mabaipost=valueSach.getKey();
                    Log.d("kiemtrafirebase",baiPostModel.toString());
                    List<BinhluanPostModel> listblp=new ArrayList<>();
                    DataSnapshot lisBinhluan=dataSnapshot.child("binhluanpost").child(baiPostModel.getMabaipost());
                    for(DataSnapshot valueBl:lisBinhluan.getChildren()){
                        BinhluanPostModel binhluanPostModel=valueBl.getValue(BinhluanPostModel.class);
                        ThanhvienModel thanhvien=dataSnapshot.child("thanhviens").child(binhluanPostModel.getMauser()).getValue(ThanhvienModel.class);
                        binhluanPostModel.setThanhvienModel(thanhvien);
                        listblp.add(binhluanPostModel);
                    }
                    ThanhvienModel thanhvien2=dataSnapshot.child("thanhviens").child(baiPostModel.getMauser()).getValue(ThanhvienModel.class);
                   baiPostModel.setThanhvienModel(thanhvien2);
                   baiPostModel.setListBL(listblp);
                    Log.d("thanhvien",thanhvien2.getAnhdaidien());
                    Log.d("thanhvien",thanhvien2.getTen());

                    baipostInterface.getBaiPostModel(baiPostModel);
                    //DataSnapshot thanhvien=dataSnapshot.child("thanhviens").child(baiPostModel.get);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dataRoot.addListenerForSingleValueEvent(valueEventListener);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tieude);
        dest.writeString(mabaipost);
        dest.writeString(ngaytao);
        dest.writeLong(sotraloi);
        dest.writeString(noidungpost);
        dest.writeTypedList(listBL);
        dest.writeString(mauser);
        dest.writeParcelable(thanhvienModel, flags);
    }
}
