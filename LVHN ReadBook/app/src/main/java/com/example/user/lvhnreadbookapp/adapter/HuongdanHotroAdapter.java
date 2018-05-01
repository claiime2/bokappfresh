package com.example.user.lvhnreadbookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.user.lvhnreadbookapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class HuongdanHotroAdapter  extends BaseExpandableListAdapter{
    Context context;
    List<String> listQuestion;
    HashMap<String,String> listdata;


    public HuongdanHotroAdapter(Context context, List<String> listQuestion, HashMap<String, String> listdata) {
        this.context = context;
        this.listQuestion = listQuestion;
        this.listdata = listdata;
    }

    @Override
    public int getGroupCount() {
        return listQuestion.size();

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;//vi chi co 1 cau tra loi
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listQuestion.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listdata.get(listQuestion.get(groupPosition));//vi tri cau hoi
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String questionTitle= (String) getGroup(groupPosition);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.item_huongdan_groupview,null);
        TextView textQuestion=convertView.findViewById(R.id.exlist_question);
        textQuestion.setText(questionTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String anser= (String) getChild(groupPosition,childPosition);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.item_huongdan_childview,null);
        TextView textAnser=convertView.findViewById(R.id.exlist_anser);
        textAnser.setText(anser);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
