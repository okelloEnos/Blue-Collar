package com.okellosoftwarez.bluecollar.Registration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.okellosoftwarez.bluecollar.R;
import com.okellosoftwarez.bluecollar.bridge.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signIn extends AppCompatActivity {
    private static FirebaseAuth signInmAuth;
    private EditText signInMail, signInPassword ;
    private Button signInBtn;
    private static String TAG = "SIGN IN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        signInmAuth = FirebaseAuth.getInstance();

        signInMail = findViewById(R.id.etSignInMail);
        signInPassword = findViewById(R.id.etPassword_signIn);

        signInBtn = findViewById(R.id.nextBtn_signIn);


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkConnected()) {
                    String email = signInMail.getText().toString().trim();
                    String password = signInPassword.getText().toString().trim();

                    if (email.isEmpty()) {
                        signInMail.setError("Missing Log In Mail");
                    } else if (password.isEmpty()) {
                        signInPassword.setError("Missing Log In PassWord");
                    } else if (password.length() < 6) {
                        signInPassword.setError("PassWord Too Short");
                    } else {
                            logInUser(email, password);
                    }

                } else {
                    Toast.makeText(signIn.this, R.string.No_network, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void logInUser(String email, String password) {

        signInmAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = signInmAuth.getCurrentUser();
                            if (user != null) {
                                updateUI();
                            }
                        } else {

                            String attempts = "We have blocked all requests from this device due to unusual activity. Try again later. [ Too many unsuccessful login attempts. Please try again later. ]";
                            if (task.getException().getMessage().equals("The email address is badly formatted.")) {
//                                Toast.makeText(SignIn.this, "Bad Format E mail...", Toast.LENGTH_LONG).show();
                                signInMail.setError("Bad Format for E-Mail Address");
                            }
                            if (task.getException().getMessage().equals("The password is invalid or the user does not have a password.")) {
                                Toast.makeText(signIn.this, "PassWord or E mail is Incorrect...", Toast.LENGTH_LONG).show();
                            }
                            if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                                Toast.makeText(signIn.this, "Your E mail is Not Registered Try Create Account...", Toast.LENGTH_LONG).show();
                            }
                            if (task.getException().getMessage().equals(attempts)) {
                                Toast.makeText(signIn.this, "Too Many Attempts !!! Get The Right Password and Try Again Later...", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }
    //    This method checks whether mobile is connected to internet and returns true if connected:
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void updateUI() {
        Intent autoIntent = new Intent(this, MainActivity.class);
        startActivity(autoIntent);
    }

    public void tv_to_signUp(View view) {
        Intent tv_signIntent = new Intent(signIn.this, signUp.class);
        startActivity(tv_signIntent);
    }

    public void forgotPassword(View view) {

            if (isNetworkConnected()) {
//            if (isInternetAvailable()){

                AlertDialog.Builder resetBuilder = new AlertDialog.Builder(this);
                resetBuilder.setTitle("Email Reset");
                resetBuilder.setMessage("Enter Email To Receive Password Reset Link");

                final EditText resetInput = new EditText(this);
                resetBuilder.setView(resetInput);
                resetBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String resetMail = resetInput.getText().toString().trim();

                        if (resetMail.isEmpty()) {
                            Toast.makeText(signIn.this, "The Email Address is Blank...", Toast.LENGTH_SHORT).show();
                        }

                        if (Patterns.EMAIL_ADDRESS.matcher(resetMail).matches()) {
                            FirebaseAuth auth = FirebaseAuth.getInstance();

                            auth.sendPasswordResetEmail(resetMail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");

                                                Toast.makeText(signIn.this, " Reset Email has been Sent to : " + resetMail, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(signIn.this, "Invalid E-Mail Address...", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                resetBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(signIn.this, "Request Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                });

                resetBuilder.show();
            } else {
                Toast.makeText(signIn.this, R.string.No_network, Toast.LENGTH_LONG).show();
            }
        }
    }
//}
