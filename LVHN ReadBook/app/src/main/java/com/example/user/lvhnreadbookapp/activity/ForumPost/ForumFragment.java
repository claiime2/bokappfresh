package com.example.user.lvhnreadbookapp.activity.ForumPost;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.lvhnreadbookapp.Models.BaiPostInterface;
import com.example.user.lvhnreadbookapp.Models.BaiPostModel;
import com.example.user.lvhnreadbookapp.Models.BinhluanPostModel;
import com.example.user.lvhnreadbookapp.R;
import com.example.user.lvhnreadbookapp.activity.DangnhapDangky.LoginActivity;
import com.example.user.lvhnreadbookapp.adapter.BaiPostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ForumFragment extends Fragment {
    ListView listView;
    BaiPostAdapter baiPostAdapter;
    ArrayList<BaiPostModel> baipostList;
    FloatingActionButton add;
    BaiPostModel baiPostModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_forum_postbai,container,false);
        listView=view.findViewById(R.id.listbaipost);

        add=view.findViewById(R.id.add_post);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Intent intent = new Intent(getContext(), AddPostActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),baipostList.get(position).getMabaipost()+" ",Toast.LENGTH_SHORT).show();
                Log.d("kiemtraintent",baipostList.get(position).getMabaipost());
                Intent intent=new Intent(getContext(),BinhluanPostActivity.class);
                BaiPostModel exa=baipostList.get(position);
                BinhluanPostModel first=new BinhluanPostModel(exa.getThanhvienModel(),exa.getNoidungpost(),exa.getNgaytao(),exa.getMabaipost());
                intent.putExtra("modelfirst",first);
                Log.d("check5",exa.getSotraloi()+"");
                intent.putExtra("sotl",String.valueOf(exa.getSotraloi()));
                intent.putExtra("title",exa.getTieude());
                intent.putParcelableArrayListExtra("postModel", (ArrayList<? extends Parcelable>) baipostList.get(position).getListBL());
                startActivity(intent);
            }
        });
            addListView();
        return view;
    }

    public void addListView() {//tieude,anhuser,tenuser,ngaytao,sotl
        baiPostModel=new BaiPostModel();
        baipostList=new ArrayList<>();



        baiPostAdapter=new BaiPostAdapter(getActivity(),R.layout.item_postbai_listview,baipostList);
            listView.setAdapter(baiPostAdapter);

        final BaiPostInterface baiPostInterface=new BaiPostInterface() {
            @Override
            public void getBaiPostModel(BaiPostModel baiPostModel) {
                baipostList.add(baiPostModel) ;
                baiPostAdapter.notifyDataSetChanged();
                Collections.sort(baipostList, new Comparator<BaiPostModel>() {
                    DateFormat f=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    @Override
                    public int compare(BaiPostModel o1, BaiPostModel o2) {
                        try {
                            return f.parse(o2.getNgaytao()).compareTo(f.parse(o1.getNgaytao()));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
            }
        };
        baiPostModel.getDataRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                baipostList.clear();
                baiPostModel.getdataPost(baiPostInterface);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
