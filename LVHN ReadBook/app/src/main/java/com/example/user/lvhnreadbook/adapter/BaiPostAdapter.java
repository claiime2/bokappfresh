package com.example.user.lvhnreadbook.adapter;

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

import com.example.user.lvhnreadbook.Models.BaiPostModel;
import com.example.user.lvhnreadbook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BaiPostAdapter extends ArrayAdapter<BaiPostModel> {
    private Context context;
    private int resource;
    private List<BaiPostModel> arrBaiPost;

    public BaiPostAdapter(Context context, int resource, ArrayList<BaiPostModel> arrBaiPost) {
        super(context, resource, arrBaiPost);
        this.context = context;
        this.resource = resource;
        this.arrBaiPost= arrBaiPost;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BaiPostAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_postbai_listview, parent, false);
            viewHolder = new BaiPostAdapter.ViewHolder();
            viewHolder.txtTieude = (TextView) convertView.findViewById(R.id.txt_tieude);
            viewHolder.txtUser = (TextView) convertView.findViewById(R.id.txt_user);
            viewHolder.txtSoTL = (TextView) convertView.findViewById(R.id.txt_sotraloi);
            viewHolder.txtNgaytao = (TextView) convertView.findViewById(R.id.txt_ngaytao);
            viewHolder.imgUser=convertView.findViewById(R.id.img_nguoidung);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BaiPostAdapter.ViewHolder) convertView.getTag();
        }
        final BaiPostModel baipost = arrBaiPost.get(position);

        viewHolder.txtTieude.setText(String.valueOf(baipost.getTieude()));
        viewHolder.txtSoTL.setText("Trả lời " +String.valueOf(baipost.getSotraloi()));
        viewHolder.txtNgaytao.setText(baipost.getNgaytao());
        viewHolder.txtUser.setText(baipost.getThanhvienModel().getTen());
        new Thread(new Runnable() {
            @Override
            public void run() {
                StorageReference storagehinhanh= FirebaseStorage.getInstance().getReference().child(baipost.getThanhvienModel().getAnhdaidien().toString());
                long ONE_MEGABYTE=1024*1024;
                storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length) ;
                        viewHolder.imgUser.setImageBitmap(bitmap);
                    }
                });
            }
        });



        return convertView;
    }

    public class ViewHolder {
        TextView txtTieude,txtUser,txtSoTL,txtNgaytao;
        ImageView imgUser;

    }

    @Override
    public int getCount() {
        int size=arrBaiPost.size();
        if(size>0&&size>4){
            return 4;
        }
        return arrBaiPost.size();
    }
}
