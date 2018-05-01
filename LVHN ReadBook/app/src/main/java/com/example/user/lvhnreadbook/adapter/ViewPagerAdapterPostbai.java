package com.example.user.lvhnreadbook.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.lvhnreadbook.activity.ForumPost.ForumFragment;
import com.example.user.lvhnreadbook.activity.ForumPost.ReviewFragment;

public class ViewPagerAdapterPostbai extends FragmentPagerAdapter {

    public ViewPagerAdapterPostbai(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position){
            case 1:
                ReviewFragment reviewFragment= new ReviewFragment();
                return reviewFragment;

            case 0:
                ForumFragment forumFragment= new ForumFragment();

                return forumFragment;

             default: return null;


        }

    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 1:
                return "Review";

            case 0:
                return "Forum";
            default:
                return super.getPageTitle(position);


        }
    }
}
