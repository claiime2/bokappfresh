package com.example.user.lvhnreadbookapp.adapter;

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
import android.widget.TextView;

import com.example.user.lvhnreadbookapp.Models.BaiPostModel;
import com.example.user.lvhnreadbookapp.Models.BinhluanPostModel;
import com.example.user.lvhnreadbookapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BinhluanPostAdapter extends ArrayAdapter<BinhluanPostModel> {
    private Context context;
    private int resource;
    private List<BinhluanPostModel> arrblPost;

    public BinhluanPostAdapter(Context context, int resource, ArrayList<BinhluanPostModel> arrblPost) {
        super(context,resource,arrblPost);
        this.context = context;
        this.resource = resource;
        this.arrblPost = arrblPost;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BinhluanPostAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_binhluan_forum, parent, false);
            viewHolder = new BinhluanPostAdapter.ViewHolder();
            viewHolder.txtUser = (TextView) convertView.findViewById(R.id.txt_tenuser);
            viewHolder.txtNgaytao = (TextView) convertView.findViewById(R.id.txt_ngaytao);
            viewHolder.imgUser=convertView.findViewById(R.id.img_user);
            viewHolder.txtNoidung=convertView.findViewById(R.id.txt_noidung);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BinhluanPostAdapter.ViewHolder) convertView.getTag();
        }
        final BinhluanPostModel bl = arrblPost.get(position);
        viewHolder.txtNgaytao.setText(bl.getNgay());
        viewHolder.txtNoidung.setText(bl.getNoidung());
        viewHolder.txtUser.setText(bl.getThanhvienModel().getTen());
        StorageReference storagehinhanh= FirebaseStorage.getInstance().getReference().child(bl.getThanhvienModel().getAnhdaidien().toString());
        long ONE_MEGABYTE=1024*1024;
        storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length) ;
                viewHolder.imgUser.setImageBitmap(bitmap);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView txtUser,txtNoidung,txtNgaytao;
        ImageView imgUser;

    }


}