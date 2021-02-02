package com.example.rationmanagementandnotificationsystem.ui.Feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

public class ShareFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        ((Home_Page)this.getActivity()).getSupportActionBar().setTitle("Feed Back");
        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.viewPager);

        String user_type= SharedSettings.readSharedSetting(getContext(),"category","Customer");
        if(user_type.equals("Admin")){
            tabLayout.addTab(tabLayout.newTab().setText("Complains"));
            tabLayout.addTab(tabLayout.newTab().setText("Completed"));

        }else{
            tabLayout.addTab(tabLayout.newTab().setText("Make Complain"));
            tabLayout.addTab(tabLayout.newTab().setText("Your Complains"));
        }


        Feedback_TabView_Adapter myAdapter=new Feedback_TabView_Adapter(getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
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
                                           }
        );
        return root;
    }
}