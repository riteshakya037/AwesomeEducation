package com.awesome_folks.awesome_education;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeScreen extends ActionBarActivity {
    ImageView imgLogo;
    LinearLayout txtLogo, txtQuotation;
    View btnSignup, btnLogin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        final Handler handler = new Handler();
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtLogo = (LinearLayout) findViewById(R.id.txtLogo);
        txtQuotation = (LinearLayout) findViewById(R.id.txtQuotation);
        btnLogin =  findViewById(R.id.loginRipple);
        btnSignup =  findViewById(R.id.signRipple);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtLogo.animate().translationY(-200).setDuration(1500).setStartDelay(500);
                imgLogo.animate().translationY(-180).scaleX((float) 0.8).scaleY((float) 0.8).setDuration(1500).setStartDelay(500);
                txtQuotation.animate().alpha(255).setDuration(4000).setStartDelay(2100);
                btnSignup.animate().translationY(0).setDuration(2000);
                btnLogin.animate().translationY(0).setDuration(1500).setStartDelay(500);
            }
        }, 5000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
