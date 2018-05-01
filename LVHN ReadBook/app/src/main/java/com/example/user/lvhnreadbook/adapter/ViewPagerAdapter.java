package com.example.user.lvhnreadbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.lvhnreadbook.DanhmucnoibatActvity;
import com.example.user.lvhnreadbook.R;
import com.example.user.lvhnreadbook.SachtheodanhmucActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by User on 11/03/2018.
 */

public class ViewPagerAdapter extends PagerAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    List<String> nameAnh;


    public ViewPagerAdapter(Context context,List<String> nameAnh){
        this.context=context;
        this.nameAnh=nameAnh;
    }
    @Override
    public int getCount() {
        return nameAnh.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.item_quangcao,null);
        //View view= layoutInflater.inflate(R.layout.item_quangcao,container,false);
        final ImageView imageView=(ImageView) view.findViewById(R.id.image_quangcao);
        //imageView.setImageResource(images[position]);
        StorageReference storagehinhanh= FirebaseStorage.getInstance().getReference().child(nameAnh.get(position));
        long ONE_MEGABYTE=1024*1024;
        storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length) ;
                imageView.setImageBitmap(bitmap);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key="";
                if(position == 0){
                    //Toast.makeText(context,"Sách mới cập nhật",Toast.LENGTH_SHORT).show();
                    key="Sách mới cập nhật";
                }else if (position == 1){
                    //Toast.makeText(context,"Sách đề cử",Toast.LENGTH_SHORT).show();
                    key="Sach đề cử";
                }else{
                    //Toast.makeText(context,"Sách đọc nhiều",Toast.LENGTH_SHORT).show();
                    key="Sách đọc nhiều";
                }
                Intent intent= new Intent(context,SachtheodanhmucActivity.class);
                intent.putExtra("keydanhmuc",key);
                context.startActivity(intent);
            }
        });


        ViewPager vp=(ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp=(ViewPager) container;
        View view=(View) object;
        vp.removeView(view);
    }
}
