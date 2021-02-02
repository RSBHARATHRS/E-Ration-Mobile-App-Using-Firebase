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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminAllComplain extends Fragment {

    private ArrayList<ComplainDataModel> comp = new ArrayList<>();
    DatabaseReference complainDB;
    ComplainViewAdapter complainViewAdapter;
    ListView list_complains;

    Spinner filter_options;

    String[] filter_opn={"All","Shopkeeper","Product","Service","Others"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_admin_all_complain, container, false);
        list_complains=root.findViewById(R.id.admin_yours_complains_listView);
        complainDB= FirebaseDatabase.getInstance().getReference();
        final Query query=complainDB.child("Complains").orderByChild("comp_status").equalTo("readed");

        filter_options= root.findViewById(R.id.com_filter_option);



        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(getContext(),R.layout.login_type_spinner_items,filter_opn);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        filter_options.setAdapter(aa);

        filter_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comp.clear();
                String text = filter_options.getSelectedItem().toString();
                if (text.equals("All")) {
                    query.addValueEventListener(new ValueEventListener() {
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
                } else {
                    complainDB.child("Complains").orderByChild("comp_obj").equalTo(text).addValueEventListener(new ValueEventListener() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
            }
        });


       /* String id= SharedSettings.readSharedSetting(getContext(),"ID","001");
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
        }*/

        Log.d("tag","onCreate");
        return root;

    }


}
