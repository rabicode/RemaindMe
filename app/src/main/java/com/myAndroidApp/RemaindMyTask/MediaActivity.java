package com.myAndroidApp.RemaindMyTask;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;

import layout.FragmentCall;
import layout.Fragmentemail;
import layout.Fragmentfacebook;
import layout.Fragmentmessage;

public class MediaActivity extends Activity {
    FragmentCall frcall = new FragmentCall();
    Fragmentmessage frmessage = new Fragmentmessage();
    Fragmentemail fremail = new Fragmentemail();
    Fragmentfacebook frfacebook = new Fragmentfacebook();


    //button action

    Button btncall,btnmessage,btnfb,btnemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_media);

        btncall = (Button) findViewById(R.id.btncall);
        btnfb = (Button) findViewById(R.id.btnfb);
        btnemail = (Button) findViewById(R.id.btnemail);
        btnmessage = (Button) findViewById(R.id.btnmessage);


        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(view);
                btncall.setVisibility(View.INVISIBLE);
                btnemail.setVisibility(View.INVISIBLE);
                btnfb.setVisibility(View.INVISIBLE);
                btnmessage.setVisibility(View.INVISIBLE);

            }
        });

        btnmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message(view);
                btncall.setVisibility(View.INVISIBLE);
                btnemail.setVisibility(View.INVISIBLE);
                btnfb.setVisibility(View.INVISIBLE);
                btnmessage.setVisibility(View.INVISIBLE);

            }
        });

        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email(view);
                btncall.setVisibility(View.INVISIBLE);
                btnemail.setVisibility(View.INVISIBLE);
                btnfb.setVisibility(View.INVISIBLE);
                btnmessage.setVisibility(View.INVISIBLE);

            }
        });

        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebook(view);
                btncall.setVisibility(View.INVISIBLE);
                btnemail.setVisibility(View.INVISIBLE);
                btnfb.setVisibility(View.INVISIBLE);
                btnmessage.setVisibility(View.INVISIBLE);

            }
        });



    }
    public void call(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment,frcall,"Call");
        ft.commit();
    }
    public void message(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment,frmessage,"message");
        ft.commit();
    }
    public void email(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment,fremail,"email");
        ft.commit();
    }




    public void facebook(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment,frfacebook,"facebook");
        ft.commit();
    }


}
