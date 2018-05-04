package com.example.user.lvhnreadbookapp.adapter;

/**
 * Created by User on 18/03/2018.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.DanhmucnoibatActvity;
import com.example.user.lvhnreadbookapp.Models.BaiPostModel;
import com.example.user.lvhnreadbookapp.Models.SachInterface;
import com.example.user.lvhnreadbookapp.Models.SachModel;
import com.example.user.lvhnreadbookapp.R;
import com.example.user.lvhnreadbookapp.SachtheodanhmucActivity;
import com.example.user.lvhnreadbookapp.SearchActivity;
import com.example.user.lvhnreadbookapp.activity.Trangchu.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class RecycleMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    int checkIndex=0;
    private ArrayList<Object> items;
    private final int VERTICAL = 1;
    private final int HORIZONTAL = 2;
    ArrayList<String> danhmuc=new ArrayList<String>(5);

    public RecycleMainAdapter(Context context, ArrayList<Object> items) {
        this.context = context;
        this.items = items;
        for(int i=0;i<items.size();i++){
            danhmuc.add("a");
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public int getItemViewType(int position) {
        checkIndex=position;
        if (position %2==0)
            return HORIZONTAL;
        if (position%2==1)
            return VERTICAL;
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case VERTICAL:
                view = inflater.inflate(R.layout.vertical_recycleview, parent, false);
                holder = new VerticalViewHolder(view);
                break;
            case HORIZONTAL:
                view = inflater.inflate(R.layout.horizon_recycleview, parent, false);
                holder = new HorizontalViewHolder(view);
                break;

            default:
                view = inflater.inflate(R.layout.horizon_recycleview, parent, false);
                holder = new HorizontalViewHolder(view);
                break;
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VERTICAL)
            verticalView((VerticalViewHolder) holder);
        else if (holder.getItemViewType() == HORIZONTAL)
            horizontalView((HorizontalViewHolder) holder);
    }
    private void horizontalView(final HorizontalViewHolder holder) {
        final HorizontalAdapter adapter;
        final ArrayList<SachModel> items=new ArrayList<>();
        final SachModel sachModel=new SachModel();
        if(checkIndex==0){

            holder.tieude.setText("Sách mới cập nhật");
            adapter = new HorizontalAdapter(items);
            final SachInterface sachInterface=new SachInterface() {
                @Override
                public void getSachModel(SachModel sachModel) {
                    items.add(sachModel) ;
                    adapter.notifyDataSetChanged();
                    sortDay(items);

                }
            };
            danhmuc.set(holder.getLayoutPosition(),"Sách mới cập nhật");
            sachModel.getdataSach(sachInterface);
            Log.d("sizelist",items.size()+"");



        }else{
            if(checkIndex==2) {
                holder.tieude.setText("Văn học nước ngoài");
                adapter = new HorizontalAdapter(items);
                final SachInterface sachInterface=new SachInterface() {
                    @Override
                    public void getSachModel(SachModel sachModel) {
                        items.add(sachModel) ;
                        adapter.notifyDataSetChanged();
                        danhmuc.set(holder.getAdapterPosition(),sachModel.getMatheloai());
                    }
                };
                sachModel.getdatatheloai(sachInterface,"Văn học nước ngoài");

            }
            else{
                holder.tieude.setText("Sách đề cử");
                adapter= new HorizontalAdapter(items);
                final SachInterface sachInterface=new SachInterface() {
                    @Override
                    public void getSachModel(SachModel sachModel) {
                        items.add(sachModel) ;
                        adapter.notifyDataSetChanged();
                        sortdanhgia(items);

                    }
                };
                sachModel.getdataSach(sachInterface);
                danhmuc.set(holder.getLayoutPosition(),"Sách đề cử");

            }
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapter);
        holder.btnthem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                // Log.d("kiemtraxemthem",holder.tieude.getText().toString());
                //Toast.makeText(context,holder.tieude.getText().toString(),Toast.LENGTH_SHORT).show();
                holder.btnthem.setTextColor(R.color.colorTrangchu);
                Intent intent= new Intent(context,SachtheodanhmucActivity.class);
                intent.putExtra("keydanhmuc",danhmuc.get(holder.getLayoutPosition()));
                intent.putExtra("vitri","trangchu");
                context.startActivity(intent);
            }
        });
    }


    private void verticalView(final VerticalViewHolder holder) {
        final ArrayList<SachModel> items=new ArrayList<>();
        final SachModel sachModel=new SachModel();
        final VerticalAdapter adapter1;
        if(checkIndex==1){
            holder.tieude.setText("Sách đọc nhiều");
            adapter1=new VerticalAdapter(items);
            final SachInterface sachInterface=new SachInterface() {
                @Override
                public void getSachModel(SachModel sachModel) {
                    items.add(sachModel) ;
                    adapter1.notifyDataSetChanged();
                    sortLuotxem(items);
                }
            };
            sachModel.getdataSach(sachInterface);
            danhmuc.set(holder.getLayoutPosition(),"Sách đọc nhiều");
        }else{
            holder.tieude.setText("Văn học trong nước");
            adapter1=new VerticalAdapter(items);
            final SachInterface sachInterface=new SachInterface() {
                @Override
                public void getSachModel(SachModel sachModel) {
                    items.add(sachModel) ;
                    adapter1.notifyDataSetChanged();
                    danhmuc.set(holder.getLayoutPosition(),sachModel.getMatheloai());
                }
            };
            sachModel.getdatatheloai(sachInterface,"Văn học trong nước");
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(adapter1);
        holder.btnthem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //Log.d("kiemtraxemthem",holder.tieude.getText().toString());
                // Toast.makeText(context,holder.tieude.getText().toString(),Toast.LENGTH_SHORT).show();
                holder.btnthem.setTextColor(R.color.colorTrangchu);
                Intent intent= new Intent(context,SachtheodanhmucActivity.class);
                intent.putExtra("keydanhmuc",danhmuc.get(holder.getLayoutPosition()));
                intent.putExtra("vitri","trangchu");
                context.startActivity(intent);
            }
        });
    }



    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView tieude;
        Button btnthem;
        HorizontalViewHolder(View itemView) {
            super(itemView);
            tieude=itemView.findViewById(R.id.tieude_view);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.inner_recyclerView);
            btnthem=itemView.findViewById(R.id.xemthem_hori);
        }
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tieude;
        Button btnthem;
        VerticalViewHolder(View itemView) {
            super(itemView);
            tieude=itemView.findViewById(R.id.tieude_view);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.inner_recyclerView);
            btnthem=itemView.findViewById(R.id.xemthem_vertical);
        }
    }
    public void sortDay(ArrayList<SachModel> items){//ngay xem moi nhat
        Collections.sort(items, new Comparator<SachModel>() {
            DateFormat f=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            @Override
            public int compare(SachModel o1,SachModel o2) {
                try {
                    return f.parse(o2.getNgaydang()).compareTo(f.parse(o1.getNgaydang()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

    }
    public void sortLuotxem(ArrayList<SachModel> items){
        Collections.sort(items, new Comparator<SachModel>() {
            @Override
            public int compare(SachModel o1,SachModel o2) {
                if (o1.getLuotxem() > o2.getLuotxem()) return -1;
                if (o1.getLuotxem() > o2.getLuotxem()) return 1;
                return 0;

            }
        });

    }
    public void sortdanhgia(ArrayList<SachModel> items){
        Collections.sort(items, new Comparator<SachModel>() {
            @Override
            public int compare(SachModel o1,SachModel o2) {
                if (o1.getDanhgia() > o2.getDanhgia()) return -1;
                if (o1.getDanhgia() > o2.getDanhgia()) return 1;
                return 0;

            }
        });

    }


}