package com.example.rationmanagementandnotificationsystem.ui.Stocks;



import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rationmanagementandnotificationsystem.R;
import com.example.rationmanagementandnotificationsystem.SupportClass;

import java.util.ArrayList;

public class CustomAdapter_Stocks extends BaseAdapter {


    Context context;
    ArrayList<Stock_datamodel> stock_datamodels;


    public CustomAdapter_Stocks(Context applicationContext, ArrayList<Stock_datamodel> stock_mod) {
        this.context = applicationContext;
        this.stock_datamodels=stock_mod;

        Log.d("tag","constructor");
    }


    @Override
    public int getCount() {
        return stock_datamodels.size();
    }


    @Override
    public Object getItem(int i) {
        return stock_datamodels.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        view = LayoutInflater.from(context).inflate(R.layout.listview_stocks, viewGroup, false);// inflate the layout

        final Stock_datamodel s = (Stock_datamodel) this.getItem(i);
        Log.d("tag","amound="+s);

        TextView amount =  view.findViewById(R.id.stock_amound);
        TextView name =  view.findViewById(R.id.stock_name);

        final String name1=s.getStock_name();
        int amountVal=s.getStock_amount();

        name.setText(name1);
        amount.setText(String.valueOf(amountVal));
        if(amountVal==0) {
            amount.setTextColor(Color.RED);
        }else{
            amount.setTextColor(Color.GREEN);
        }
        //Log.d("tag","name="+s.getStock_name());
        //Log.d("tag","amound="+s.getStock_amount());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportClass s=new SupportClass(context);
                if(!s.isCustomer()) {
                    StockUpdate stockUpdate = new StockUpdate(context, name1);
                    stockUpdate.displayStockUpdate();
                }else {
                    Toast.makeText(context,name1,Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}

