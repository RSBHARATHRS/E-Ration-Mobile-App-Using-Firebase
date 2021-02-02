package com.example.rationmanagementandnotificationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ModeSelection extends AppCompatActivity {
    Button simple_mode;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

        simple_mode=findViewById(R.id.ms_btn_sm);

        simple_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(ModeSelection.this,Home_Page.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
