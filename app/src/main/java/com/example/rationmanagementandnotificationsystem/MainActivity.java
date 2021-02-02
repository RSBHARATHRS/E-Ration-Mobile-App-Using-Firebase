package com.example.rationmanagementandnotificationsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseFirestore db1 = FirebaseFirestore.getInstance();
    DocumentReference docref;
    Intent intent;
    Spinner cat_spin;
    Button login;
    EditText et_username,et_password;
    String username,password;
    String[] cat_g={"Customer","Shop keeper","Admin"};
    String cat_selected;
    //private String login_url = "http://e-ration.epizy.com/e-ration/login.php";    //local host address http://10.0.2.2/e-ration/login.php

    //FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    //private DocumentReference docRef;

    private static final String KEY_EMPTY = "";

    SharedPreferences sharedPreferences;
    ProgressDialog pDialog;
    JSONObject jsonObject;
    SharedPreferences.Editor editor;
    String collection="";
    static boolean log_status=false;

    //-------------------------------------------------------------onCreate Method------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          //sharedPreferences.getBoolean("login_status",false
            //intent = new Intent(MainActivity.this, Home_Page.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("cat_selected", cat_selected);
            //startActivity(intent);
            //finish();


        Log.i("tag","onCreate");
        login=findViewById(R.id.login);
        et_username=findViewById(R.id.edtText_UserID);
        et_password=findViewById(R.id.edtText_Password);
        jsonObject = new JSONObject();

        sharedPreferences=getSharedPreferences("app_status",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("login_status", false);
        editor.commit();
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        cat_spin= findViewById(R.id.spinner_category);
        cat_spin.setOnItemSelectedListener(this);


        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.login_type_spinner_items,cat_g);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        cat_spin.setAdapter(aa);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=et_username.getText().toString();
                password=et_password.getText().toString();
                if(validateInputs()) {
                    displayLoader();
                    login();
                }
            }
        });
    }

//-----------------------------------------------------------onChangePassword Method---------------------------------------------------------//
    public void onChangePassword(View view){
        intent=new Intent(this, Change_Password.class);
        startActivity(intent);
    }


//--------------------------------------------------------------onRestart Method------------------------------------------------------------//
    @Override
    protected void onRestart() {
        super.onRestart();

        //Log.i("tag","onRestart");
    }

//---------------------------------------------------------------onPause Method-------------------------------------------------------------//

    @Override
    protected void onPause() {
        super.onPause();

        /*String id=SharedSettings.readSharedSetting(MainActivity.this,"ID","001");
        String cat=SharedSettings.readSharedSetting(MainActivity.this,"category","Customer_profile");

        docRef=firebaseFirestore.collection(cat).document(id);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    //String name = documentSnapshot.getString("Name");
                    String type = documentSnapshot.getString("Type");
                    String phone_no = documentSnapshot.getString("Phone_no");
                    String add = documentSnapshot.getString("Address");


                    //SharedSettings.saveSharedSetting(MainActivity.this,"user_name",name);
                    SharedSettings.saveSharedSetting(MainActivity.this,"user_type",type);
                    SharedSettings.saveSharedSetting(MainActivity.this,"user_phone_no",phone_no);
                    SharedSettings.saveSharedSetting(MainActivity.this,"user_add",add);


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //Log.i("tag","onPause");*/
    }

//--------------------------------------------------------------onResume Method---------------------------------------------------------------//
    @Override
    protected void onResume() {
        super.onResume();
        //Log.i("tag","onResume");
    }

//-----------------------------------------------------------methods for spinner Method---------------------------------------------------------//

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        cat_selected=cat_g[position];
        //SharedSettings.saveSharedSetting(MainActivity.this,"category",cat_selected);

        if(cat_selected.equals("Customer")){
            collection="Customer_profile";
            SharedSettings.saveSharedSetting(MainActivity.this,"category",collection);
        }
        else if(cat_selected.equals("Shop keeper")){
            collection="Shop_keeper_profile";
            SharedSettings.saveSharedSetting(MainActivity.this,"category",collection);
        }else {
            collection="Admin";
            SharedSettings.saveSharedSetting(MainActivity.this,"category",collection);
        }
        Toast.makeText(getApplicationContext(), cat_selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//---------------------------------------------------------------ValidateInput Method------------------------------------------------------------//
    private boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            et_username.setError("Username cannot be empty");
            et_username.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
             et_password.setError("Password cannot be empty");
             et_password.requestFocus();
             return false;
        }
        return true;
    }

//--------------------------------------------------------------displayLoader Method------------------------------------------------------------//
    private void displayLoader() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    //----------------------------------------------------------------Login Method--------------------------------------------------------------//
   /* private void login() {
        //displayLoader();
        JSONArray request = new JSONArray();
        try {
            //Populate the request parameters
            JSONObject jo = new JSONObject();
            jo.put("KEY_USERNAME", username);
            jo.put("KEY_PASSWORD", password);

            request.put(0,jo);
            //request.put(KEY_USERNAME, username);
            //request.put(KEY_PASSWORD, password);
            //Log.i("tag", String.valueOf(request));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                login_url,
                request,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("tag","jsonOnResponse");
                        //pDialog.dismiss();
                        try {
                            //for(int i=0;i<response.length();i++){
                            // Get current json object
                            JSONObject student = response.getJSONObject(0);

                            // Get the current student (json object) data
                            String user_name = student.getString("smart_card_ID");
                            String user_pass = student.getString("user_password");

                            if (user_pass.equals(password)) {

                                //for store login status into status sharedPreference file
                                //editor.putBoolean("login_status", false);
                                editor.putBoolean("login_status", true);
                                editor.commit();
                                log_status=true;
                                intent = new Intent(MainActivity.this, ModeSelection.class);
                                intent.putExtra("cat_selected", cat_selected);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                                et_password.setError("Incorrect Password");
                                et_password.requestFocus();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }*/
    public void login(){
        docref = db1.collection(collection).document(username);
        Log.i("tag","login_function");
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.i("tag","onSuccess");
                if(documentSnapshot.exists()){
                    String s_name=documentSnapshot.getString("Name");
                    String s_pass=documentSnapshot.getString("Password");
                    String type = documentSnapshot.getString("Type");
                    String phone_no = documentSnapshot.getString("Phone_no");
                    String add = documentSnapshot.getString("Address");
                    String url = documentSnapshot.getString("Url");
                    if (s_pass.equals(password)) {

                        //for store login status into status sharedPreference file
                        //editor.putBoolean("login_status", false);
                        editor.putBoolean("login_status", true);
                        editor.commit();
                        log_status=true;
                        intent = new Intent(MainActivity.this, Home_Page.class);
                        intent.putExtra("cat_selected", cat_selected);
                        Log.i("tag","name="+s_name);
                        SharedSettings.saveSharedSetting(MainActivity.this, "login_status", "true");
                        SharedSettings.saveSharedSetting(MainActivity.this, "user_name", s_name);
                        SharedSettings.saveSharedSetting(MainActivity.this, "ID", username);
                        SharedSettings.saveSharedSetting(MainActivity.this,"user_type",type);
                        SharedSettings.saveSharedSetting(MainActivity.this,"user_phone_no",phone_no);
                        SharedSettings.saveSharedSetting(MainActivity.this,"user_add",add);
                        SharedSettings.saveSharedSetting(MainActivity.this,"user_pic",url);


                        pDialog.dismiss();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                        et_password.setError("Incorrect Password");
                        et_password.requestFocus();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                Log.i("tag","faile");
                pDialog.dismiss();
            }
        });



    }
}
