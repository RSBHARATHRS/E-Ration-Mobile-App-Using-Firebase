package com.example.rationmanagementandnotificationsystem.ui.Stocks;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    ListView list_view_stocks;
    //String product_names[] = {"rice","sugar","kerosen"};
    //String stocks_amound[] = {"50","60","80"};
    CustomAdapter_Stocks customAdapter;
    DatabaseReference db;

    ArrayList<Stock_datamodel> stock_model = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stocks, container, false);
        ((Home_Page)this.getActivity()).getSupportActionBar().setTitle("Stocks Availability");


        list_view_stocks =  root.findViewById(R.id.stocks_list_view);
        Log.i("s","onCreateView");

        db = FirebaseDatabase.getInstance().getReference();
        db.child("Stocks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stock_model.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.d("tag","ds="+ds);
                        Stock_datamodel stk_model = ds.getValue(Stock_datamodel.class);            //Now get Teacher Objects and populate our arraylist.
                        stock_model.add(stk_model);
                        Log.d("tag","111="+stk_model);
                    }
                    customAdapter = new CustomAdapter_Stocks(getContext(), stock_model);
                    list_view_stocks.setAdapter(customAdapter);

                    new Handler().post(new Runnable() {

                        @Override
                        public void run() {
                            list_view_stocks.smoothScrollToPosition(stock_model.size());
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("mTAG", databaseError.getMessage());
                Toast.makeText(getContext(), "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

            Log.i("s","after Custom adapter");

            list_view_stocks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // set an Intent to Another Activity
                    //Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    //intent.putExtra("image", logos[position]); // put image data in Intent
                    //startActivity(intent); // start Intent
                }
            });


        return root;
    }
}