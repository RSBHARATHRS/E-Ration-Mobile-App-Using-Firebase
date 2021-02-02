package com.example.rationmanagementandnotificationsystem.ui.Feedback;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.History;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.Today;

public class Feedback_TabView_Adapter extends FragmentPagerAdapter {

    Context myContext;
    int totalTabs;
    String category;


    public Feedback_TabView_Adapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        Log.i("tab","MyAdapter");
        category= SharedSettings.readSharedSetting(context,"category","Customer_profile");
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.i("tab","case1");
                if(category.equals("Admin")){
                    AdminAllComplain adminAllComplain=new AdminAllComplain();
                    return adminAllComplain;

                }else {
                    Feedback_MakeComplain feedback_makeComplain = new Feedback_MakeComplain();
                    return feedback_makeComplain;
                }

            case 1:
                if(category.equals("Admin")){
                    AdminComCompleted adminComCompleted=new AdminComCompleted();
                    return adminComCompleted;

                }else {
                    Log.i("tab", "case2");
                    YoursComplains yoursComplains = new YoursComplains();
                    return yoursComplains;
                }

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
