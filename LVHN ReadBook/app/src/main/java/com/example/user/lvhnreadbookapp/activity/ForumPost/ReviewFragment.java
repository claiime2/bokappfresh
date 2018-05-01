package com.example.user.lvhnreadbookapp.activity.ForumPost;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.user.lvhnreadbookapp.Models.BaiPostModel;
import com.example.user.lvhnreadbookapp.Models.BinhluanModel;
import com.example.user.lvhnreadbookapp.R;
import com.example.user.lvhnreadbookapp.adapter.BaiPostAdapter;
import com.example.user.lvhnreadbookapp.adapter.BinhluanAdapter;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {
    ListView listView;
    BinhluanAdapter blAdapter;
    ArrayList<BinhluanModel> blList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_review_postbai,container,false);
        listView=view.findViewById(R.id.list_binhluan);

        addListView();
        return view;
    }
    public void addListView() {//ten,anh,nd,tensach
        blList=new ArrayList<BinhluanModel>();
        blList.add(new BinhluanModel("Nguyenhgugu",R.drawable.user,"Sach này quá dỡ, mất thời gian","Thời kỳ đồ đá"));
        blList.add(new BinhluanModel("Nguyenhgugu",R.drawable.user,"Sach này ời gian","Thời kỳ đồ đá"));
        blList.add(new BinhluanModel("Nguyenhgerfvugu",R.drawable.user,"Sach này quá dỡ, mất thời gian","Thời kỳ đồ đá"));
        blList.add(new BinhluanModel("Nguyenhgudddu",R.drawable.user,"Sach thời gian","Thời kỳ đồ đá"));
        blList.add(new BinhluanModel("Nguyeaewenhgugu",R.drawable.user,"Sach này quá dỡ, mất thời gian","Thời kỳ đồ đá"));
        blList.add(new BinhluanModel("Nguyenhgudddgu",R.drawable.user,"Sach này quá dỡ, mấan","Thời kỳ đồ đá"));

       blAdapter=new BinhluanAdapter(getActivity(),R.layout.item_binluan_listview,blList);
        listView.setAdapter(blAdapter);





    }
}
