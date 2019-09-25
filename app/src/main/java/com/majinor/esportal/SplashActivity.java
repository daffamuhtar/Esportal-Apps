package com.majinor.esportal;

import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    Animation frombottom,fromtop;
    ImageView splashlogo;
    private int waktu_loading=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashlogo = (ImageView)findViewById(R.id.splash_logo);
//        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

//        splashlogo.startAnimation(frombottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent log=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(log);
                overridePendingTransition(R.anim.faded_in,R.anim.faded_out);

                finish();

            }
        },waktu_loading);
    }
}
