package com.example.rationmanagementandnotificationsystem;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class SupportClass {
    Context context;

    Calendar cal;
    int year,month,dayofMonth,m;
    int random;


    String complainID;
    String timeStamp;
    String today_date;

    String user_type;

    public SupportClass(Context ctx) {
        this.context=ctx;
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        dayofMonth=cal.get(Calendar.DAY_OF_MONTH);
        m=month;
        ++m;
        today_date=dayofMonth+"/"+m+"/"+year;
        Log.i("today",today_date);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        timeStamp=simpleDateFormat.format(cal.getTime());
    }
    public String getComplainID(String id) {
        random = new Random().nextInt(91) + 20;
        complainID=id.concat(String.valueOf(random));
        return complainID;
    }
    public String getToday_date() {
        return today_date;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    ProgressDialog pDialog;
     void displayLoader() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Fetching data... Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    void stopDisplayLoader(){
         pDialog.dismiss();
    }


    public boolean isCustomer(){
        user_type= SharedSettings.readSharedSetting(context,"category","Customer_profile");
        if(user_type.equals("Customer_profile")){
            return true;
        }

         return false;
    }
    public boolean isShopKeeper(){
        user_type= SharedSettings.readSharedSetting(context,"category","Customer_profile");
        if(user_type.equals("Shop_keeper_profile")){
            return true;
        }

        return false;
    }
    public boolean isAdmin(){
        user_type= SharedSettings.readSharedSetting(context,"category","Customer_profile");
        if(user_type.equals("Admin")){
            return true;
        }

        return false;
    }

}
