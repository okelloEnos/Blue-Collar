package com.okellosoftwarez.bluecollar.Registration;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.okellosoftwarez.bluecollar.R;
import com.okellosoftwarez.bluecollar.bridge.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signUp extends AppCompatActivity {
    private static FirebaseAuth mAuth;

    EditText etPassword, etUserName, etPhone, etMail, etConfirmPassword, etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etUserName = findViewById(R.id.etUserName);
        etPhone = findViewById(R.id.etPhone);
        etConfirmPassword = findViewById(R.id.etConfirmPassword_signUp);
        etLocation = findViewById(R.id.etLocation);

        Button registerBtn = findViewById(R.id.registerBtn_signUp);

        TextView logBtn = findViewById(R.id.logInTxt_signUp);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(signUp.this, signIn.class);
                startActivity(logIntent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkConnected()) {

                    String email = etMail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    String confirmPassword = etConfirmPassword.getText().toString().trim();
                    String userName = etUserName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String location = etLocation.getText().toString().trim();

                    if (userName.isEmpty()) {
                        etUserName.setError("User Name Required");

                    } else if (email.isEmpty()) {
                        etMail.setError("E-Mail Address Required");

                    } else if (phone.isEmpty()) {
                        etPhone.setError("Phone Number Required");

                    } else if (phone.length() < 10) {
                        etPhone.setError("Phone Number Too Short");

                    } else if (location.isEmpty()) {
                        etLocation.setError("Location Required");

                    } else if (password.isEmpty()) {
                        etPassword.setError("Password Required");

                    } else if (password.length() < 6) {
                        etPassword.setError("PassWord Too Short");

                    } else if (confirmPassword.isEmpty()) {
                        etConfirmPassword.setError("Confirm Password Required");

                    } else if (!password.equals(confirmPassword)) {
                        etConfirmPassword.setError("Confirm Password Do not Match ");

                    }
                    else {

                        registerUser(email, password);

                    }

                } else {
                    Toast.makeText(signUp.this, R.string.No_network, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void updateUI() {
        Intent autoIntent = new Intent(this, MainActivity.class);
        startActivity(autoIntent);
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                updateUI();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
                                Toast.makeText(signUp.this, "Your E mail is already registered Try Log In...", Toast.LENGTH_LONG).show();
                            }

                            if (task.getException().getMessage().equals("The email address is badly formatted.")) {
//                                Toast.makeText(SignUp.this, "Bad Format E mail...", Toast.LENGTH_LONG).show();
                                etMail.setError("Bad Format for E-Mail Address");
                            }
                        }

                        // ...
                    }
                });
    }


}
