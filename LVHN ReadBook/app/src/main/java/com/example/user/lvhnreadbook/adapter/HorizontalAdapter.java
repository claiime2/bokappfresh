package com.example.user.lvhnreadbook.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;

/**
 * Created by User on 18/03/2018.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.lvhnreadbook.ChitietsachActivity;
import com.example.user.lvhnreadbook.DanhmucnoibatActvity;
import com.example.user.lvhnreadbook.Models.SachModel;
import com.example.user.lvhnreadbook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        ArrayList<SachModel> data = new ArrayList<>();

        public HorizontalAdapter(ArrayList<SachModel> data) {
            this.data = data;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizon_recycleview, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.txtname.setText(data.get(position).getTensach());
            StorageReference storagehinhanh= FirebaseStorage.getInstance().getReference().child(data.get(position).getHinhanh().toString());
            long ONE_MEGABYTE=1024*1024;
            storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length) ;
                    holder.image.setImageBitmap(bitmap);
                }
            });
        }

        @Override
        public int getItemCount() {
                int size=data.size();
                if(size>0&&size>6){
                    return 6;
                }
                return data.size();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView image;
            TextView txtname;

            public MyViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.imgHinh);
                txtname = (TextView) itemView.findViewById(R.id.txtName);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos=getLayoutPosition();
                //Log.d("kiemtralik2",data.get(pos).getTensach()+"  "+pos+" "+" horizon");
                //Toast.makeText(itemView.getContext(),"masach"+data.get(pos).getMasach()+"theloai"+ data.get(pos).getMatheloai()+data.get(pos).getNgaydang(),Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(itemView.getContext(),ChitietsachActivity.class);
                intent.putExtra("keysach",data.get(pos).getMasach());
                intent.putExtra("keytheloai",data.get(pos).getMatheloai());
                itemView.getContext().startActivity(intent);
            }
        }
    }

