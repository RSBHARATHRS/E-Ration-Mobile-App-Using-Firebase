package com.example.rationmanagementandnotificationsystem.ui.Todays_activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;


public class Today extends Fragment{

    /*******************************************************************MAIN ACTIVITY CONTINUATION********************************************/

    DatabaseReference db;
    FireBaseHelper helper;
    CustomAdapter adapter;
    ListView listView;
    Spinner Spinner_product_name;
    String[] spin_catalogue;//={"Rice","Sugar","Palm Oil","Kerosene"};
    Calendar cal;

    String temp;
    String today_date,ID;
    int year,month,dayofMonth,m;
    //int maxId=0;
    String user_type;

    int random;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_today, container, false);

        db = FirebaseDatabase.getInstance().getReference();


        listView=root.findViewById(R.id.todays_product_listview);
        FloatingActionButton fab = root.findViewById(R.id.fab_today);

        //cal=Calendar.getInstance();

        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayofMonth=calendar.get(Calendar.DAY_OF_MONTH);
        m=month;
        ++m;
        today_date=dayofMonth+"/"+m+"/"+year;
        Log.i("today",today_date);

        temp= String.valueOf(dayofMonth);
        temp=temp.concat(String.valueOf(m));
        temp=temp.concat(String.valueOf(year));

        //Log.i("today",temp);
        random = new Random().nextInt(91) + 20;
        ID=temp.concat(String.valueOf(random));

        helper = new FireBaseHelper(db, getContext(), listView);
        //maxId=history.maxId;


        user_type= SharedSettings.readSharedSetting(getContext(),"category","Customer_profile");
        if(user_type.equals("Shop_keeper_profile") || user_type.equals("Admin")){
            fab.show();
            Query query = db.child("Stocks").orderByChild("stock_amount").startAt(1);
            query.addListenerForSingleValueEvent(queryValueListener);
        }else {
            fab.hide();
        }

        displayLoader();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.smoothScrollToPosition(4);
                displayInputDialog();
            }
        });


        return root;
    }




    /*************************************************************FIREBASE HELPER START********************************************************/
    private class FireBaseHelper {

        DatabaseReference db;
        Boolean saved;
        ArrayList<Product_datamodel> data_model = new ArrayList<>();
        ListView mListView;
        Context c;

        /*
       let's receive a reference to our FirebaseDatabase
       */
        public FireBaseHelper(DatabaseReference db, Context context, ListView mListView) {
            this.db = db;
            this.c = context;
            this.mListView = mListView;
            this.retrieve();
            Log.i("list","helperclass");
        }


        private ArrayList<Product_datamodel> retrieve() {
            db.child("Product").orderByChild("date").equalTo(today_date).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    data_model.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        //maxId= (int) dataSnapshot.getChildrenCount();
                        //Log.i("today","initial_count"+maxId);

                        random = new Random().nextInt(61) + 20;
                        ID=temp.concat(String.valueOf(random));
                        Log.i("today","random="+random);

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Product_datamodel teacher = ds.getValue(Product_datamodel.class);            //Now get Teacher Objects and populate our arraylist.
                            data_model.add(teacher);
                        }
                        adapter = new CustomAdapter(c, data_model);
                        mListView.setAdapter(adapter);

                        new Handler().post(new Runnable() {

                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(data_model.size());
                            }
                        });
                    }
                    else {
                        pDialog.dismiss();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("mTAG", databaseError.getMessage());
                    Toast.makeText(c, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            return data_model;
        }

        private Boolean save(Product_datamodel teacher) {
            Log.i("today","saveFunction");
            Log.i("today","ID="+ID);
            //check if they have passed us a valid teacher. If so then return false.
            if (teacher == null) {
                saved = false;
            } else {
                //otherwise try to push data to firebase database.
                try {
                    //push data to FirebaseDatabase. Table or Child called Product will be created.
                    db.child("Product").child(ID).setValue(teacher);
                    saved = true;
                    Log.i("today","saved");

                } catch (DatabaseException e) {
                    e.printStackTrace();
                    saved = false;
                }
            }
            //tell them of status of save.
            return saved;
        }
    }

    /**********************************************************CUSTOM ADAPTER START***********************************************************/
    private class CustomAdapter extends BaseAdapter {
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
            ImageView Product_image=convertView.findViewById(R.id.product_image);
            TextView time_stamp=convertView.findViewById(R.id.time_stamp);


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
    Button saveBtn;
    private void displayInputDialog() {
        //create input dialog
        Dialog d = new Dialog(getContext());
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setTitle("Save To FireBase");
        d.setContentView(R.layout.input_dialog_todays_product);

        cal=Calendar.getInstance();



        //String[] spin_catalogue={"Rice","Sugar","Palm Oil","Kerosene"};





        //find widgets
        Spinner_product_name = d.findViewById(R.id.spinner_proudct_name);
        Et_date = d.findViewById(R.id.EditText_date);
        Et_price = d.findViewById(R.id.descEditText);
        saveBtn = d.findViewById(R.id.saveBtn);
        calen=d.findViewById(R.id.calendar);
        //Creating the ArrayAdapter instance having the blank name list
        ArrayAdapter<String> aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spin_catalogue);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_product_name.setAdapter(aa);


        Et_date.setText(today_date);
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
                    String date = Et_date.getText().toString();
                    String price = Et_price.getText().toString();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                    String timeStamp=simpleDateFormat.format(cal.getTime());

                    //set data to POJO
                    Product_datamodel s = new Product_datamodel();
                    s.setProduct_name(name);
                    s.setDate(date);
                    s.setPrice(price);
                    s.setId(ID);
                    s.setTime(timeStamp);

                    //perform simple validation
                    if (name.length() > 0) {
                        //save data to firebase
                        if (helper.save(s)) {
                            //clear edittexts
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
    }


    String key_empty="";
    private boolean validateInputs() {
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
    ValueEventListener queryValueListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            spin_catalogue=new String[(int) dataSnapshot.getChildrenCount()];
            Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
            Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

            int i=0;
            while (iterator.hasNext()) {
                DataSnapshot next =  iterator.next();

                //spin_catalogue.add(next.child("stock_name").getValue());
                String str= (String) next.child("stock_name").getValue();
                Log.i("tag", "Value = " + str);
                spin_catalogue[i]=str;
                i++;
            }
            Log.i("tag", "Value = " + spin_catalogue[0]);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
