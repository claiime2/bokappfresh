package com.example.user.lvhnreadbookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.DocsachActivity;
import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.R;

import java.io.File;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
        private Context context;
        private List<SachModel> list;

    public RecyclerViewAdapter(Context context, List<SachModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.cardview_sach,parent, false);
            return new MyViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        SachModel sach = list.get(position);
        File file=new File(sach.getHinhanh());
        Log.d("anh",sach.getHinhanh());
        Bitmap bmHDD = BitmapFactory.decodeFile(file.getAbsolutePath());
        viewHolder.imgsach.setImageBitmap(bmHDD);
        viewHolder.txtten.setText(list.get(position).getTensach());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,DocsachActivity.class);
//                intent.putExtra("TenSach", list.get(position).getTensach());
                intent.putExtra("path",list.get(position).getFile());
                context.startActivity(intent);
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgsach;
        private TextView txtten;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgsach = (ImageView) itemView.findViewById(R.id.imagesach);
            txtten = (TextView) itemView.findViewById(R.id.textviewten);
            cardView = itemView.findViewById(R.id.cardview_id);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
