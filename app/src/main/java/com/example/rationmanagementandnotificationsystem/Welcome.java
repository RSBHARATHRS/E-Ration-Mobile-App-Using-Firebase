package com.example.rationmanagementandnotificationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {
    private static int timeout=2000;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        logo=findViewById(R.id.welcome);

        Animation animation= AnimationUtils.loadAnimation(Welcome.this,R.anim.welcome_screen_animation);
        logo.startAnimation(animation);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean Check = Boolean.valueOf(SharedSettings.readSharedSetting(Welcome.this, "login_status", "false"));
                if(Check){
                    Intent intent=new Intent(Welcome.this,Home_Page.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(Welcome.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },timeout);
    }
}
