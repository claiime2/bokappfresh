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

import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class SachtheodanhmucAdapter extends ArrayAdapter<SachModel> {
    private List<SachModel> sachModelList;
    private int resource;
    private Activity context;

    public SachtheodanhmucAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<SachModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        sachModelList = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView txtTen = (TextView) v.findViewById(R.id.textTensach);
        final ImageView imgHinh = (ImageView) v.findViewById(R.id.imageHinhsach);

        txtTen.setText(sachModelList.get(position).getTensach());
        StorageReference storagehinhanh= FirebaseStorage.getInstance().getReference().child(sachModelList.get(position).getHinhanh().toString());
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
