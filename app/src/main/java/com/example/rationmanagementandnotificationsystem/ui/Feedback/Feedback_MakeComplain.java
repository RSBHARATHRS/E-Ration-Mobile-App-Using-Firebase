package com.example.rationmanagementandnotificationsystem.ui.Feedback;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.example.rationmanagementandnotificationsystem.SupportClass;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.Product_datamodel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.Calendar;


public class Feedback_MakeComplain extends Fragment {
    RadioGroup radioGroup;
    Button btn_submit;
    EditText Et_others,Et_name,Et_phone_no,Et_desc;
    TextView others;
    String id,category;
    Boolean saved;
    FirebaseFirestore firebaseFirestore;
    DatabaseReference complainDB;

    String complainID,complainObj;
    //ViewPager viewPager1;
    //ShareFragment shareFragment;

    //RadioButton radioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_feedback_complain, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        complainDB= FirebaseDatabase.getInstance().getReference();


        id= SharedSettings.readSharedSetting(getContext(),"ID","001");
        category= SharedSettings.readSharedSetting(getContext(),"category","001");

        Et_name=root.findViewById(R.id.et_name);
        Et_phone_no=root.findViewById(R.id.et_phone_no);
        radioGroup=root.findViewById(R.id.btn_radio_grp);
        others=root.findViewById(R.id.txt_others);
        Et_others = root.findViewById(R.id.et_others);
        Et_desc=root.findViewById(R.id.et_desc);
        btn_submit=root.findViewById(R.id.btn_comp_submit);


        //shareFragment=new ShareFragment();
        //viewPager1=shareFragment.viewPager;


        others.setVisibility(View.INVISIBLE);
        Et_others.setVisibility(View.INVISIBLE);

        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radio","onCheckedChange ID:"+checkedId);

                RadioButton radioButton = group.findViewById(checkedId);
                complainObj=radioButton.getText().toString();
                if(checkedId==R.id.radio_other)
                {
                    Log.i("radio","onCheckedChange_in");
                    Et_others.setVisibility(View.VISIBLE);
                    others.setVisibility(View.VISIBLE);
                }
                else {
                    Et_others.setVisibility(View.INVISIBLE);
                    others.setVisibility(View.INVISIBLE);
                }
                Log.i("radio","onCheckedChange_out="+complainObj);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Et_name.getText().toString();
                String desc=Et_desc.getText().toString();
                String Ph_no=Et_phone_no.getText().toString();
                //String =Et_name.getText().toString();
                //String name=Et_.getText().toString();

                ComplainDataModel cdm=new ComplainDataModel();
                SupportClass sup=new SupportClass(getContext());

                complainID=sup.getComplainID(id);

                if(complainObj.equals("Others")){
                    complainObj=Et_others.getText().toString();
                }

                cdm.setComp_id(complainID);
                cdm.setComp_date(sup.getToday_date());
                cdm.setComp_time(sup.getTimeStamp());
                cdm.setComp_user_name(name);
                cdm.setComp_obj(complainObj);
                cdm.setComp_desc(desc);
                cdm.setComp_user_phone_no(Ph_no);
                cdm.setSmartCard_ID(id);
                cdm.setComp_status("submitted");

                if(save(cdm)){
                    Toast.makeText(getActivity(), "submitted", Toast.LENGTH_SHORT).show();
                    //viewPager1.setCurrentItem(1);
                }

                /*int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getActivity(),"Select your form type", Toast.LENGTH_SHORT).show();
                }
                else {

                    RadioButton radioButton = radioGroup.findViewById(selectedId);

                    // Now display the value of selected item
                    // by the Toast message
                    //Et_others.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        return root;
    }




    private Boolean save(final ComplainDataModel complainDataModel) {

        if (complainDataModel == null) {
            saved = false;
        } else {
            try {
                DocumentReference docRef=firebaseFirestore.collection(category).document(id).collection("Complains").document(complainID);
                docRef.set(complainDataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        complainDB.child("Complains").child(complainID).setValue(complainDataModel);

                        Toast.makeText(getContext(),"Your Complain Saved Succefully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                saved = true;
                Log.i("com","saved");

            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        Et_name.setText("");
        Et_phone_no.setText("");
        Et_desc.setText("");
        //tell them of status of save.
        return saved;
    }
}
