package com.example.rationmanagementandnotificationsystem.ui.Feedback;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SupportClass;
import com.example.rationmanagementandnotificationsystem.ui.Todays_activity.Product_datamodel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

 class ComplainViewAdapter extends BaseAdapter {

    Context ctx;
    ArrayList<ComplainDataModel> teachers;

    public ComplainViewAdapter(Context context, ArrayList<ComplainDataModel> teachers) {
        this.ctx = context;
        this.teachers = teachers;
    }

    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Object getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.listview_complains, parent, false);
        }
        YoursComplains yoursComplains;
        SupportClass s=new SupportClass(ctx);
        //ImageView delete=convertView.findViewById(R.id.listBtn_delete);

        TextView txt_comp_id = convertView.findViewById(R.id.complain_id);
        TextView txt_comp_date = convertView.findViewById(R.id.complain_date);
        //TextView txt_comp_time = convertView.findViewById(R.id.complain_time);
        //TextView txt_comp_user_name = convertView.findViewById(R.id.complain_username);
        TextView txt_comp_obj = convertView.findViewById(R.id.complain_objective);
        ImageView new_msg=convertView.findViewById(R.id.new_comp);
        ImageView new_com_del=convertView.findViewById(R.id.new_comp_delete);
        //TextView txt_comp_desc = convertView.findViewById(R.id.complain_desc);

        final ComplainDataModel cdm = (ComplainDataModel) this.getItem(position);

        txt_comp_id.setText(cdm.getComp_id());
        txt_comp_date.setText(cdm.getComp_date());
        //txt_comp_time.setText(cdm.getComp_time());
        //txt_comp_user_name.setText(cdm.getComp_user_name());
        txt_comp_obj.setText(cdm.getComp_obj());
        //txt_comp_desc.setText(cdm.getComp_desc());
        new_msg.setVisibility(View.INVISIBLE);
        new_com_del.setVisibility(View.INVISIBLE);
        if(cdm.getComp_status().equals("submitted") && s.isAdmin()){
            new_msg.setVisibility(View.VISIBLE);
        }else if((s.isCustomer() || s.isShopKeeper()) && cdm.getComp_status().equals("submitted")){
            new_com_del.setVisibility(View.VISIBLE);
        }

        new_com_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db_delete= FirebaseDatabase.getInstance().getReference();
                db_delete.child("Complains").child(cdm.getComp_id()).removeValue();
            }
        });


        //pDialog.dismiss();
        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportClass s=new SupportClass(ctx);
                if(s.isAdmin() && cdm.getComp_status().equals("submitted")) {
                    DisplayComplainDetails displayComplainDetails = new DisplayComplainDetails(ctx, cdm);
                    displayComplainDetails.display();
                    displayComplainDetails.read();
                }else {
                    DisplayComplainDetails displayComplainDetails = new DisplayComplainDetails(ctx, cdm);
                    displayComplainDetails.display();
                }
                //Toast.makeText(ctx, cdm.getComp_user_name(), Toast.LENGTH_SHORT).show();     //List view selection portion
                //Log.d("tag","user Name="+cdm.getComp_user_name());
            }
        });

        return convertView;
    }
}