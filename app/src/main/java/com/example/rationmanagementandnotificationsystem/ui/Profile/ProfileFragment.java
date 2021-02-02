package com.example.rationmanagementandnotificationsystem.ui.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.MainActivity;
import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SharedSettings;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {


    TextView t_name,t_id,t_type,t_ph_no,t_add;
    Button home;
    ImageView profile_pic;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("life","onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("life","onCreate");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("life","onCreateView");
        View root=inflater.inflate(R.layout.fragment_profile, container, false);
        home = root.findViewById(R.id.btn_logout);
        ((Home_Page)this.getActivity()).getSupportActionBar().setTitle("Profile");

        t_name=root.findViewById(R.id.profile_name);
        t_id=root.findViewById(R.id.profile_id);
        t_type=root.findViewById(R.id.profile_card_type);
        t_ph_no=root.findViewById(R.id.profile_ph_no);
        t_add=root.findViewById(R.id.profile_add);
        profile_pic=root.findViewById(R.id.img_profile);


        String cat=SharedSettings.readSharedSetting(getContext(),"category","Customer_profile");

        String id=SharedSettings.readSharedSetting(getContext(),"ID","001");
        String name=SharedSettings.readSharedSetting(getContext(),"user_name","no name");
        String type=SharedSettings.readSharedSetting(getContext(),"user_type","FFFF");
        String phone_no=SharedSettings.readSharedSetting(getContext(),"user_phone_no","no number");
        String add=SharedSettings.readSharedSetting(getContext(),"user_add","no name");
        String url=SharedSettings.readSharedSetting(getContext(),"user_pic","no pic");

        Picasso.get().load(url).into(profile_pic);

        t_id.setText(id);
        t_name.setText(name);
        t_type.setText(type);
        t_ph_no.setText(phone_no);
        t_add.setText(add);

        /*docRef=firebaseFirestore.collection(cat).document(id);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String name = documentSnapshot.getString("Name");
                    String type = documentSnapshot.getString("Type");
                    String phone_no = documentSnapshot.getString("Phone_no");
                    String add = documentSnapshot.getString("Address");


                    t_name.setText(name);
                    t_type.setText(type);
                    t_ph_no.setText(phone_no);
                    t_add.setText(add);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedSettings.saveSharedSetting(getContext(), "login_status", "false");
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }


}
