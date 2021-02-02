package com.example.rationmanagementandnotificationsystem.ui.Feedback;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminComCompleted extends Fragment {

    private ArrayList<ComplainDataModel> comp = new ArrayList<>();
    DatabaseReference complainDB;
    ComplainViewAdapter complainViewAdapter;
    ListView list_complains;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_admin_com_completed, container, false);

        list_complains=root.findViewById(R.id.admin_complains_completed_listView);
        complainDB= FirebaseDatabase.getInstance().getReference();


        complainDB.child("Complains").orderByChild("comp_status").equalTo("completed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comp.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        //maxId= (int) dataSnapshot.getChildrenCount();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Log.d("tag","ds="+ds);
                        ComplainDataModel com_data_mode = ds.getValue(ComplainDataModel.class);            //Now get Teacher Objects and populate our arraylist.
                        comp.add(com_data_mode);
                    }
                        complainViewAdapter = new ComplainViewAdapter(getContext(), comp);
                        list_complains.setAdapter(complainViewAdapter);

                        new Handler().post(new Runnable() {

                            @Override
                            public void run() {
                                list_complains.smoothScrollToPosition(comp.size());
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("tag", databaseError.getMessage());
                    Toast.makeText(getContext(), "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });




        Log.d("tag","onCreate");



        return root;
    }



}
