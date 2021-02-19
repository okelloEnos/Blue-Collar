package com.okellosoftwarez.bluecollar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.okellosoftwarez.bluecollar.bridge.FirstScreen;

import java.net.InetAddress;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requesting for a window with no title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setting splash for full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        final Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    Intent retryIntent = new Intent(getApplicationContext(), Retry.class);
                    if (isNetworkConnected()) {
                        if (isInternetAvailable()) {
                            Intent startIntent = new Intent(getApplicationContext(), FirstScreen.class);
                            startActivity(startIntent);
                            finish();
                        } else {

                            retryIntent.putExtra("internet", "first");
                            startActivity(retryIntent);
                        }

                    } else {
                        retryIntent.putExtra("internet", "second");
                        startActivity(retryIntent);
                    }

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
    }

    //    This method checks whether mobile is connected to internet and returns true if connected:
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    //    This method actually checks if device is connected to internet(There is a possibility it's connected to a network but not to internet).
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");

            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
