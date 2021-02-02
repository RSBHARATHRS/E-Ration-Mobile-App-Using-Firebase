package com.example.rationmanagementandnotificationsystem.ui.Stocks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StockUpdate {



    Context context;
    String stock_name;
    DatabaseReference update_stk=FirebaseDatabase.getInstance().getReference();

    public StockUpdate(Context ctx,String name) {
        this.context=ctx;
        this.stock_name=name;

    }

    void displayStockUpdate(){

        final Dialog d_stk = new Dialog(context);
        d_stk.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d_stk.setTitle("Complain Details");
        d_stk.setContentView(R.layout.input_dialog_stock_update);

        TextView stk_name;
        final EditText stk_amount;
        Button btn_up;

        stk_name=d_stk.findViewById(R.id.stk_update_name);
        stk_amount=d_stk.findViewById(R.id.stk_update_amount);
        btn_up=d_stk.findViewById(R.id.btn_update_stk);



        stk_name.setText(stock_name);
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int amount = Integer.parseInt(stk_amount.getText().toString());
                    Map<String, Object> taskMap = new HashMap<String, Object>();
                    taskMap.put("stock_amount", amount);
                    update_stk.child("Stocks").child(stock_name).updateChildren(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context,"Updated Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });

        d_stk.show();
    }
}
