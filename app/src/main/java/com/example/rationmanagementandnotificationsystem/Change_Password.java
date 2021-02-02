package com.example.rationmanagementandnotificationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Change_Password extends AppCompatActivity {
    JSONObject json_obj_req,res;
    ProgressDialog pDialog;

    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    DocumentReference docRef;
    private String SmartCardID,CardHolderName,CardHolderDob,NewPassword,ConfPassword;

    //private String change_pass_url = "http://10.0.2.2/e-ration/change_password.php";
    private EditText et_smartCarsID, et_cardHolderName, et_cardHolderDob, et_newPassword ,et_confPassword;
    private Button change_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        et_smartCarsID = findViewById(R.id.chPass_Et_SmartCardID);
        et_cardHolderName = findViewById(R.id.chPass_Et_CardHolderName);
        et_cardHolderDob = findViewById(R.id.chPass_Et_dob);
        et_newPassword = findViewById(R.id.chPass_Et_Newpassword);
        et_confPassword = findViewById(R.id.chPass_Et_ConfirmPassword);

        change_pass = findViewById(R.id.chPass_btn_Change);



        /*change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartCardID = et_smartCarsID.getText().toString();
                CardHolderName = et_cardHolderName.getText().toString();
                CardHolderDob = et_cardHolderDob.getText().toString();
                NewPassword = et_newPassword.getText().toString();
                ConfPassword = et_confPassword.getText().toString();
                if (validateInputs()) {
                    if (NewPassword.equals(ConfPassword)) {
                        try {
                            json_obj_req = new JSONObject();
                            json_obj_req.put("KEY_SMARTCARDID", SmartCardID);
                            json_obj_req.put("KEY_CARDHOLDERNAME", CardHolderName);
                            json_obj_req.put("KEY_CARDHOLDERDOB", CardHolderDob);
                            json_obj_req.put("KEY_NEWPASSWORD", NewPassword);
                            Log.i("tag", String.valueOf(json_obj_req));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                change_pass_url,
                                json_obj_req,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("msg");
                                            if (res.equals("OK")) {
                                                Toast.makeText(getApplicationContext(), "Successfully Password Changed", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Change_Password.this, MainActivity.class);
                                                intent.putExtra("smart_card_ID",SmartCardID);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                        MySingleton.getInstance(Change_Password.this).addToRequestQueue(jsonObjectRequest);

                    } else {
                        Toast toast = Toast.makeText(Change_Password.this, "Confirm password doesn't match with new password", Toast.LENGTH_LONG);
                        toast.show();
                        et_confPassword.setError("not matched with new password");
                        et_confPassword.requestFocus();
                    }
                }
            }
        });*/

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartCardID = et_smartCarsID.getText().toString();
                CardHolderName = et_cardHolderName.getText().toString();
                CardHolderDob = et_cardHolderDob.getText().toString();
                NewPassword = et_newPassword.getText().toString();
                ConfPassword = et_confPassword.getText().toString();
                if (validateInputs()) {
                    if (NewPassword.equals(ConfPassword)) {
                        displayLoader();
                        docRef = firebaseFirestore.collection("Customer_profile").document(SmartCardID);
                        check();
                    } else {
                        Toast toast = Toast.makeText(Change_Password.this, "Confirm password doesn't match with new password", Toast.LENGTH_LONG);
                        toast.show();
                        et_confPassword.setError("not matched with new password");
                        et_confPassword.requestFocus();
                    }
                }
            }
        });
    }
    private void check(){
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String name=documentSnapshot.getString("Name");
                    String DOB=documentSnapshot.getString("DOB");
                    if(name.equals(CardHolderName) && DOB.equals(CardHolderDob)){
                        change();
                    }
                    else {
                        Toast toast = Toast.makeText(Change_Password.this, "Incorrect username or DOB", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void change(){
        Map<String,Object> note=new HashMap<>();
        note.put("Password",NewPassword);
        docRef.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(Change_Password.this, MainActivity.class);
                intent.putExtra("smart_card_ID",SmartCardID);
                pDialog.dismiss();
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private boolean validateInputs() {
        if(SmartCardID.equals("")){
            et_smartCarsID.setError("Smart card ID cannot be empty");
            et_smartCarsID.requestFocus();
            return false;
        }
        if(CardHolderName.equals("")){
            et_cardHolderName.setError("Card holder name cannot be empty");
            et_cardHolderName.requestFocus();
            return false;
        }
        if(CardHolderDob.equals("")){
            et_cardHolderDob.setError("Card holder DOB cannot be empty");
            et_cardHolderDob.requestFocus();
            return false;
        }
        if(NewPassword.equals("")){
            et_newPassword.setError("New password cannot be empty");
            et_newPassword.requestFocus();
            return false;
        }
        if(ConfPassword.equals("")){
            et_confPassword.setError("Confirm Password cannot be empty");
            et_confPassword.requestFocus();
            return false;
        }
        return true;
    }
    private void displayLoader() {
        pDialog = new ProgressDialog(Change_Password.this);
        pDialog.setMessage("Changing Password.. Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }

}

