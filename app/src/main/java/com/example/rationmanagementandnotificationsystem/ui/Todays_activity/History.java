package com.example.rationmanagementandnotificationsystem.ui.Todays_activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rationmanagementandnotificationsystem.R;

import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class History extends Fragment{

    /*******************************************************************MAIN ACTIVITY CONTINUATION********************************************/

    DatabaseReference db,db1;
    private FirebaseHelper helper;
    CustomAdapter adapter;
    ListView listView;
    //Spinner Spinner_product_name;
    //String[] spin_catalogue={"Rice","Sugar","Palm Oil","Kerosene"};
    Calendar cal;

    String user_type;

    public int maxId=0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_history, container, false);

        db = FirebaseDatabase.getInstance().getReference();
        db1 = FirebaseDatabase.getInstance().getReference();
        listView=root.findViewById(R.id.history_product_listView);
        helper = new FirebaseHelper(db, getContext(), listView);
        cal=Calendar.getInstance();
        //FloatingActionButton fab = root.findViewById(R.id.fab);


        displayLoader();
        user_type= SharedSettings.readSharedSetting(getContext(),"category","Customer_profile");

        /*
        displayLoader();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.smoothScrollToPosition(4);
                displayInputDialog();
            }
        });*/
        return root;
    }


    /*************************************************************FIREBASE HELPER START********************************************************/
    private class FirebaseHelper {

        DatabaseReference db;
        //Boolean saved;
        ArrayList<Product_datamodel> teachers = new ArrayList<>();
        ListView mListView;
        Context c;

        /*
       let's receive a reference to our FirebaseDatabase
       */
        public FirebaseHelper(DatabaseReference db, Context context, ListView mListView) {
            this.db = db;
            this.c = context;
            this.mListView = mListView;
            this.retrieve();
            Log.i("list","helperclass");
        }

        //let's now write how to save a single Teacher to FirebaseDatabase

       /* public Boolean save(Product_datamodel teacher) {
            //check if they have passed us a valid teacher. If so then return false.
            if (teacher == null) {
                saved = false;
            } else {
                //otherwise try to push data to firebase database.
                try {
                    //push data to FirebaseDatabase. Table or Child called Product will be created.
                    db.child("Product").child(String.valueOf(maxId+1)).setValue(teacher);
                    saved = true;

                } catch (DatabaseException e) {
                    e.printStackTrace();
                    saved = false;
                }
            }
            //tell them of status of save.
            return saved;
        }*/

        /*
        Retrieve and Return them clean data in an arraylist so that they just bind it to ListView.
         */

        public ArrayList<Product_datamodel> retrieve() {
            db.child("Product").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    teachers.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        //maxId= (int) dataSnapshot.getChildrenCount();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Log.d("tag","ds="+ds);
                            Product_datamodel teacher = ds.getValue(Product_datamodel.class);            //Now get Teacher Objects and populate our arraylist.
                            teachers.add(teacher);
                        }
                        adapter = new CustomAdapter(c, teachers);
                        mListView.setAdapter(adapter);

                        new Handler().post(new Runnable() {

                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(teachers.size());
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("mTAG", databaseError.getMessage());
                    Toast.makeText(c, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            return teachers;
        }
    }

    /**********************************************************CUSTOM ADAPTER START***********************************************************/
    public class CustomAdapter extends BaseAdapter {
        Context c;
        ArrayList<Product_datamodel> teachers;

        public CustomAdapter(Context c, ArrayList<Product_datamodel> teachers) {
            this.c = c;
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
                convertView = LayoutInflater.from(c).inflate(R.layout.listview_todays_product, parent, false);
            }

            ImageView delete=convertView.findViewById(R.id.listBtn_delete);
            final TextView nameTextView = convertView.findViewById(R.id.product_name);
            TextView quoteTextView = convertView.findViewById(R.id.date);
            TextView descriptionTextView = convertView.findViewById(R.id.price);
            TextView time_stamp=convertView.findViewById(R.id.time_stamp);

            ImageView Product_image=convertView.findViewById(R.id.product_image);
            final Product_datamodel s = (Product_datamodel) this.getItem(position);

            String product_name=s.getProduct_name();

            if(product_name.equals("Rice")){
                Product_image.setImageResource(R.drawable.rice);
            }else if(product_name.equals("Sugar")){
                Product_image.setImageResource(R.drawable.sugar);
            }else if(product_name.equals("Palm Oil")){
                Product_image.setImageResource(R.drawable.palm_oil);
            }else if(product_name.equals("Kerosene")){
                Product_image.setImageResource(R.drawable.kerosene);
            }
            nameTextView.setText(s.getProduct_name());
            quoteTextView.setText(s.getDate());
            descriptionTextView.setText(s.getPrice());
            time_stamp.setText(s.getTime());
            pDialog.dismiss();
            //ONITECLICK
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, s.getProduct_name(), Toast.LENGTH_SHORT).show();     //List view selection portion
                }
            });
            if(user_type.equals("Shop_keeper_profile")){
                delete.setVisibility(View.VISIBLE);
            }else {
                delete.setVisibility(View.INVISIBLE);
            }
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("del","id:"+s.getId());
                    DatabaseReference db_delete=db;
                    db_delete.child("Product").child(s.getId()).removeValue();
                }
            });
            return convertView;
        }
    }

    EditText Et_date, Et_price;
    String name;
    ImageView calen;
   /* public void displayInputDialog() {
        //create input dialog
        Dialog d = new Dialog(getContext());
        d.setTitle("Save To FireBase");
        d.setContentView(R.layout.input_dialog_todays_product);

        //find widgets
        Spinner_product_name = d.findViewById(R.id.spinner_proudct_name);
        Et_date = d.findViewById(R.id.EditText_date);
        Et_price = d.findViewById(R.id.descEditText);
        Button saveBtn = d.findViewById(R.id.saveBtn);
        calen=d.findViewById(R.id.calendar);
        //Creating the ArrayAdapter instance having the blank name list
        ArrayAdapter<String> aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spin_catalogue);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_product_name.setAdapter(aa);

        Calendar calendar=Calendar.getInstance();
        final  int year=calendar.get(Calendar.YEAR);
        final  int month=calendar.get(Calendar.MONTH);
        final  int dayofMonth=calendar.get(Calendar.DAY_OF_MONTH);
        int m=month;
        ++m;
        Et_date.setText(dayofMonth+"/"+m+"/"+year);
        calen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ++month;
                        name=dayOfMonth+"/"+month+"/"+year;
                        Et_date.setText(name);
                    }
                },year,month,dayofMonth);
                datePickerDialog.show();
            }
        });

        //save button clicked
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Log.i("tag", "OnSave");

                    //get data from editTexts
                    String name = Spinner_product_name.getSelectedItem().toString();
                    Log.i("tag", "name" + name);
                    String date = Et_date.getText().toString();
                    String price = Et_price.getText().toString();

                    //set data to POJO
                    Product_datamodel s = new Product_datamodel();
                    s.setProduct_name(name);
                    s.setDate(date);
                    s.setPrice(price);
                    s.setId(String.valueOf(maxId+1));

                    //perform simple validation
                    if (name != null && name.length() > 0) {
                        //save data to firebase
                        if (helper.save(s)) {

                            //clear edittexts
                            Et_date.setText("");
                            Et_price.setText("");

                            //refresh listview
                            ArrayList<Product_datamodel> fetchedData = helper.retrieve();
                            adapter = new CustomAdapter(getContext(), fetchedData);
                            listView.setAdapter(adapter);
                            listView.smoothScrollToPosition(fetchedData.size());
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Name Must Not Be Empty Please", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        d.show();
    }*/


    String key_empty="";
    public boolean validateInputs() {
        if(Et_price.getText().toString().equals(key_empty)){
            Et_price.setError("You must enter Product price");
            Et_price.requestFocus();
            return false;
        }
        return true;
    }


    ProgressDialog pDialog;
    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Fetching data... Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }

}