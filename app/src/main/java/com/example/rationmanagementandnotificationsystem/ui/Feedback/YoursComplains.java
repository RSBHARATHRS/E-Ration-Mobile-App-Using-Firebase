package com.example.rationmanagementandnotificationsystem.ui.Feedback;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.History;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.Product_datamodel;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.Today;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YoursComplains extends Fragment {
    ArrayList<ComplainDataModel> comp = new ArrayList<>();
    DatabaseReference complainDB;
    ComplainViewAdapter complainViewAdapter;
    ListView list_complains;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_yours_complains, container, false);
        list_complains=root.findViewById(R.id.yours_complains_listView);
        complainDB=FirebaseDatabase.getInstance().getReference();

        String id= SharedSettings.readSharedSetting(getContext(),"ID","001");
        String user_type= SharedSettings.readSharedSetting(getContext(),"category","Customer_profile");

        if(user_type.equals("Admin")){
            complainDB.child("Complains").addValueEventListener(new ValueEventListener() {
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
        }else {
            complainDB.child("Complains").orderByChild("smartCard_ID").equalTo(id).addValueEventListener(new ValueEventListener() {
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
        }





        Log.d("tag","onCreate");

        return root;
    }

    /*public void displayInputDialog(ComplainDataModel com) {
        //create input dialog
        Dialog d = new Dialog(getContext());
        //d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setTitle("Complain Details");
        d.setContentView(R.layout.input_dialog_complains);

        Log.d("tag","dialodBox");

        //find widgets
        TextView comID;  //,comDate,comTime,comName,comObj,comDesc;
        //comID = d.findViewById(R.id.complain_id1);
        //comDate = d.findViewById(R.id.complain_date);
        //comTime = d.findViewById(R.id.complain_time);
        //comName = d.findViewById(R.id.complain_username);
        //comObj = d.findViewById(R.id.complain_objective);
        //comDesc = d.findViewById(R.id.complain_desc);


        //comID.setText(com.getComp_id());
        //comDate.setText(com.getComp_date());
        //comTime.setText(com.getComp_time());
        //comName.setText(com.getComp_user_name());
        //comObj.setText(com.getComp_obj());
        //comDesc.setText(com.getComp_desc());

        d.show();

        //Creating the ArrayAdapter instance having the blank name list



        //save button clicked

    }


   /* ProgressDialog pDialog;
    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Fetching data... Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }*/



}
