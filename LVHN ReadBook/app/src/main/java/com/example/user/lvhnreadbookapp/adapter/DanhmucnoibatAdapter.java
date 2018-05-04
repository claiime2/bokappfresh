package com.example.user.lvhnreadbookapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.lvhnreadbookapp.Models.DanhMuc;
import com.example.user.lvhnreadbookapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class DanhmucnoibatAdapter extends ArrayAdapter<DanhMuc> {
    private List<DanhMuc> danhmucList;
    private int resource;
    private Activity context;

    public DanhmucnoibatAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<DanhMuc> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        danhmucList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView txtTen = (TextView) v.findViewById(R.id.tenDanhmuc);
        final ImageView imgHinh = (ImageView) v.findViewById(R.id.hinhDanhmuc);

        txtTen.setText(danhmucList.get(position).getTen());
        StorageReference storagehinhanh= FirebaseStorage.getInstance().getReference().child(danhmucList.get(position).getAnhtheloai().toString());
        long ONE_MEGABYTE=1024*1024;
        storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length) ;
                imgHinh.setImageBitmap(bitmap);
            }
        });
        return v;
    }

}
