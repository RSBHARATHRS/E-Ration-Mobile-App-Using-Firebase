package com.example.rationmanagementandnotificationsystem.ui.Feedback;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rationmanagementandnotificationsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DisplayComplainDetails {
    DatabaseReference update_com_status= FirebaseDatabase.getInstance().getReference();

        ComplainDataModel com;//=new ComplainDataModel();
        Context context;
        //String id;


        public DisplayComplainDetails(Context ctx,ComplainDataModel com1) {

            this.com=com1;
            this.context=ctx;


        }

        void display() {
            //create input dialog
            final Dialog d = new Dialog(context);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            d.setTitle("Complain Details");
            d.setContentView(R.layout.input_dialog_complains);

            Log.d("tag", "dialodBox");

            //find widgets
            TextView comID,comDate,comTime,comName,comObj,comDesc,ok,phone_no;
            comID = d.findViewById(R.id.complain_id1);
            comDate = d.findViewById(R.id.complain_date1);
            comTime = d.findViewById(R.id.complain_time1);
            comName = d.findViewById(R.id.complain_username1);
            comObj = d.findViewById(R.id.complain_objective1);
            comDesc = d.findViewById(R.id.complain_desc1);
            phone_no = d.findViewById(R.id.complain_phone_no1);
            ok = d.findViewById(R.id.ok);

            //id=com.getComp_id();
            comID.setText(com.getComp_id());
            comDate.setText(com.getComp_date());
            comTime.setText(com.getComp_time());
            comName.setText(com.getComp_user_name());
            comObj.setText(com.getComp_obj());
            comDesc.setText(com.getComp_desc());
            phone_no.setText(com.getComp_user_phone_no());

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });

           // Map<String, Object> taskMap = new HashMap<String, Object>();
            //taskMap.put("comp_status", "readed");
           // update_com_status.child("Complains").child(com.getComp_id()).updateChildren(taskMap);

            d.show();

            //Creating the ArrayAdapter instance having the blank name list


            //save button clicked

        }
        public void read(){
            Map<String, Object> taskMap = new HashMap<String, Object>();
            taskMap.put("comp_status", "readed");
            update_com_status.child("Complains").child(com.getComp_id()).updateChildren(taskMap);
        }


    }