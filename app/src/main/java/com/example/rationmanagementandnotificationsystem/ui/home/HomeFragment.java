package com.example.rationmanagementandnotificationsystem.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.example.rationmanagementandnotificationsystem.ui.About.AboutUsFragment;
import com.example.rationmanagementandnotificationsystem.ui.Profile.ProfileFragment;
import com.example.rationmanagementandnotificationsystem.ui.Stocks.ToolsFragment;
import com.example.rationmanagementandnotificationsystem.ui.Feedback.ShareFragment;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.GalleryFragment;
import com.example.rationmanagementandnotificationsystem.ui.Map.MapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    CardView profile,tdy_activity,geolocation,notification,feedback;
    ToggleButton simpleToggleButton;
    FragmentTransaction ft;
    //Toolbar tb;
    Home_Page hp;
    LinearLayout cusomer,shop_keeper;
    TextView cus_open,about_us;
    ImageView check;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //tb=root.findViewById(R.id.toolbar);

        ft=getFragmentManager().beginTransaction();

        profile = root.findViewById(R.id.cardview_profile);
        tdy_activity = root.findViewById(R.id.cardview_today_activity);
        geolocation = root.findViewById(R.id.cardview_geo_location);
        notification = root.findViewById(R.id.cardview_notification);
        feedback = root.findViewById(R.id.cardview_feedback);
        simpleToggleButton = root.findViewById(R.id.simpleToggleButton); // initiate a toggle button
        //Boolean ToggleButtonState = simpleToggleButton.isChecked();

        cusomer=root.findViewById(R.id.li_customer);
        cus_open=root.findViewById(R.id.cus_open);
        about_us=root.findViewById(R.id.home_about_us);
        check=root.findViewById(R.id.check);


        shop_keeper=root.findViewById(R.id.li_shop_keeper);

        String user_type= SharedSettings.readSharedSetting(getContext(),"category","Customer");
        if(user_type.equals("Shop_keeper_profile")){
            cusomer.setVisibility(View.INVISIBLE);

        }else {
            shop_keeper.setVisibility(View.INVISIBLE);
            databaseReference.child("Store_status").child("open").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String temp=dataSnapshot.getValue(String.class);
                    if(temp.equals("YES")){
                        check.setColorFilter(Color.GREEN);
                        cus_open.setText("Shop is Opened");
                        cus_open.setTextColor(Color.DKGRAY);
                    }else {
                        check.setColorFilter(Color.GRAY);
                        cus_open.setText("Shop is Closed");
                        cus_open.setTextColor(Color.GRAY);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        simpleToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String store_status= (String) simpleToggleButton.getText();
                Log.i("toggle",store_status);

                databaseReference.child("Store_status").child("open").setValue(store_status);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                ProfileFragment newfragment = new ProfileFragment();
                swapFragment(newfragment);
                //tb.setTitle("profile");
                //hp.getSupportActionBar().setTitle("Profile");

            }
        });
        tdy_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFragment newGamefragment = new GalleryFragment();
                swapFragment(newGamefragment);
            }
        });
        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment newfragment = new MapFragment();
                swapFragment(newfragment);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolsFragment newfragment = new ToolsFragment();
                swapFragment(newfragment);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareFragment newfragment = new ShareFragment();
                swapFragment(newfragment);
            }
        });
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                swapFragment(aboutUsFragment);
            }
        });


        return root;
    }

    private void swapFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Page)this.getActivity()).getSupportActionBar().setTitle("Home");
    }
}
