package com.example.rationmanagementandnotificationsystem.ui.Todays_activity;


import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        Log.i("tab","MyAdapter");
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.i("tab","case1");
                Today today = new Today();
                return today;
            case 1:
                Log.i("tab","case2");
                History history = new History();
                return history;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        Log.i("tab","getCount");
        return totalTabs;
    }

}
