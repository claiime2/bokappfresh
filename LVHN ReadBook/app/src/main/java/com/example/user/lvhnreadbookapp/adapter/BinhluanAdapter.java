package com.example.user.lvhnreadbookapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.lvhnreadbookapp.Models.BinhluanModel;
import com.example.user.lvhnreadbookapp.Models.BinhluanPostModel;
import com.example.user.lvhnreadbookapp.R;

import java.util.ArrayList;
import java.util.List;

public class BinhluanAdapter extends ArrayAdapter<BinhluanModel> {
    private Context context;
    private int resource;
    private List<BinhluanModel> arrBL;

    public BinhluanAdapter(Context context, int resource, ArrayList<BinhluanModel> arrBL) {
        super(context, resource, arrBL);
        this.context = context;
        this.resource = resource;
        this.arrBL = arrBL;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BinhluanAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_binluan_listview, parent, false);
            viewHolder = new BinhluanAdapter.ViewHolder();
            viewHolder.txtUser = (TextView) convertView.findViewById(R.id.txt_tenuser);
            viewHolder.txtTensach= (TextView) convertView.findViewById(R.id.txt_tensach);
            viewHolder.imgUser=convertView.findViewById(R.id.img_user);
            viewHolder.txtNoidung=convertView.findViewById(R.id.txt_noidung);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BinhluanAdapter.ViewHolder) convertView.getTag();
        }
        final BinhluanModel bl = arrBL.get(position);

        viewHolder.txtUser.setText(bl.getTenuser());
        viewHolder.txtTensach.setText(bl.getTensach());
        viewHolder.imgUser.setImageResource(bl.getAnhuser());


        return convertView;
    }

    public class ViewHolder {
        TextView txtUser,txtNoidung,txtTensach;
        ImageView imgUser;

    }


}