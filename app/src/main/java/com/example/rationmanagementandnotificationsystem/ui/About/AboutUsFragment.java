package com.example.rationmanagementandnotificationsystem.ui.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.ui.home.HomeFragment;

public class AboutUsFragment extends Fragment {


    Button back_to_home;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_about, container, false);
        //final TextView textView = root.findViewById(R.id.text_send);
        ((Home_Page)this.getActivity()).getSupportActionBar().setTitle("About us");

        back_to_home=root.findViewById(R.id.btn_back_to_home);
        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                swapFragment(homeFragment);
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
}