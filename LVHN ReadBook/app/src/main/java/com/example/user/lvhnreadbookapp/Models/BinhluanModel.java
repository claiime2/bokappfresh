package com.example.user.lvhnreadbookapp.Models;

public class BinhluanModel {
    String mabinhluan;
    String tenuser;
    int anhuser;
    String noidungbl;
    String tensach;

    public BinhluanModel(String tenuser, int anhuser, String noidungbl, String tensach) {
        this.tenuser = tenuser;
        this.anhuser = anhuser;
        this.noidungbl = noidungbl;
        this.tensach = tensach;
    }

    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    public String getTenuser() {
        return tenuser;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }

    public int getAnhuser() {
        return anhuser;
    }

    public void setAnhuser(int anhuser) {
        this.anhuser = anhuser;
    }

    public String getNoidungbl() {
        return noidungbl;
    }

    public void setNoidungbl(String noidungbl) {
        this.noidungbl = noidungbl;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }
}
