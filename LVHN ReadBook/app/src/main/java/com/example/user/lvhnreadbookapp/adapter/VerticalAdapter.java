package com.example.user.lvhnreadbookapp.adapter;

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


import com.example.user.lvhnreadbookapp.ChitietsachActivity;
import com.example.user.lvhnreadbookapp.DanhmucnoibatActvity;
import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.R;
import com.example.user.lvhnreadbookapp.activity.Trangchu.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;



    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {
        private ArrayList<SachModel> data = new ArrayList<>();

        public VerticalAdapter(ArrayList<SachModel> data) {
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_recycleview, parent, false);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.tenSach.setText(data.get(position).getTensach());
            holder.tacgia.setText(data.get(position).getTacgia());
            holder.luotxem.setText("Lượt xem "+String.valueOf(data.get(position).getLuotxem()));
            holder.ngaydang.setText(data.get(position).getNgaydang());
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
            if(size>0&&size>5){
                return 5;
            }
            return data.size();

        }


        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tenSach,tacgia,luotxem,ngaydang;
            ImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);
                tenSach = (TextView) itemView.findViewById(R.id.tensach);
                tacgia = (TextView) itemView.findViewById(R.id.tacgia);
                luotxem = (TextView) itemView.findViewById(R.id.luotxem);
                ngaydang =(TextView) itemView.findViewById(R.id.ngaydang);
                image= itemView.findViewById(R.id.imageVer);
                itemView.setOnClickListener(this);


            }

            @Override
            public void onClick(View v) {
                int pos=getLayoutPosition();
                //Log.d("kiemtralik",data.get(pos).getMasach()+"theloai"+ data.get(pos).getMatheloai()+" vertical");
                //Toast.makeText(itemView.getContext(),"masach"+data.get(pos).getMasach()+"theloai"+ data.get(pos).getMatheloai()+" ",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(itemView.getContext(),ChitietsachActivity.class);
                intent.putExtra("keysach",data.get(pos).getMasach());
                intent.putExtra("keytheloai",data.get(pos).getMatheloai());
                itemView.getContext().startActivity(intent);
            }
        }

    }