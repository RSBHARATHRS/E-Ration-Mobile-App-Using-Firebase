package com.example.rationmanagementandnotificationsystem.ui.Todays_activity;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.R;
import com.google.android.material.tabs.TabLayout;


public class GalleryFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("tab", "onCreateView");
        View root = inflater.inflate(R.layout.fragment_today__tab_view, container, false);
        ((Home_Page) this.getActivity()).getSupportActionBar().setTitle("Today's Activity");

        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        //tabLayout.addTab(tabLayout.newTab().setText("Movie"));


        Log.i("tab", "onCreateView1");

        MyAdapter adapter = new MyAdapter(getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        Log.i("tab", "onCreateView2");

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }




}
