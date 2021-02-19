package com.okellosoftwarez.bluecollar.bridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.okellosoftwarez.bluecollar.R;
import com.okellosoftwarez.bluecollar.Registration.signIn;
import com.okellosoftwarez.bluecollar.Registration.signUp;

public class FirstScreen extends AppCompatActivity {
Button signIn, signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        signIn = findViewById(R.id.signInBtn);
        signOut = findViewById(R.id.signUpBtn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = new Intent(FirstScreen.this, signIn.class);
                startActivity(signInIntent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signOutIntent = new Intent(FirstScreen.this, signUp.class);
                startActivity(signOutIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent backIntent = new Intent();
        backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
