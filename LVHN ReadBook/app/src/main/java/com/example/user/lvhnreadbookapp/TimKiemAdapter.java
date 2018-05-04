package com.example.user.lvhnreadbookapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TimKiemAdapter extends ArrayAdapter {
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(resource,parent,false);
        final ImageView ivAnhBia;
        TextView tvTenSach;
        TextView tvTacGia;
        TextView tvLuotXem;
        RatingBar rbDanhGia;
        TextView tvMoTa;
        ivAnhBia=(ImageView) convertView.findViewById(R.id.ivAnhBia);
        tvTenSach=(TextView) convertView.findViewById(R.id.tvTenSach);
        tvTacGia=(TextView) convertView.findViewById(R.id.tvTacGia);
        tvLuotXem=(TextView) convertView.findViewById(R.id.tvLuotXem);
        tvMoTa=(TextView) convertView.findViewById(R.id.tvMoTa);
        rbDanhGia=(RatingBar) convertView.findViewById(R.id.rbDanhGia);
        SachModel sachModel=object.get(position);
        StorageReference islandRef = storageRef.child(sachModel.getHinhanh());
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ivAnhBia.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        tvTenSach.setText(sachModel.getTensach());
        tvTacGia.setText(sachModel.getTacgia());
        tvLuotXem.setText(String.valueOf(sachModel.getLuotxem()));
        tvMoTa.setText(sachModel.getGioithieu());
        rbDanhGia.setRating((int)sachModel.getDanhgia());
        return convertView;
    }
    private Context context;
    private int resource;
    private ArrayList<SachModel> object;
    public TimKiemAdapter(Context context, int resource, ArrayList<SachModel> object){
        super(context,resource,object);
        this.context=context;
        this.resource=resource;
        this.object=object;
    }
}
