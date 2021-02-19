package com.okellosoftwarez.bluecollar.viewScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.okellosoftwarez.bluecollar.R;
import com.okellosoftwarez.bluecollar.bridge.FirstScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    ImageView profileImage;
    TextView tvName, tvMail, tvPhone, tvLocation, tvSpeciality ;
    FirebaseAuth mechAuth;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logOut:
//                logging out implementation
//                Toast.makeText(this, "Log Out Implementation Coming Soon", Toast.LENGTH_SHORT).show();
//                mechAuth.signOut();
                Intent logIntent = new Intent(this, FirstScreen.class);
                startActivity(logIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Profile");

        profileImage = findViewById(R.id.imageMechanicProfile);
        tvName = findViewById(R.id.tvName);
        tvMail = findViewById(R.id.tvMail);
        tvPhone = findViewById(R.id.tvPhone);
        tvLocation = findViewById(R.id.tvLocation);
        tvSpeciality = findViewById(R.id.tvSpeciality);

        personalProductsIntents();


    }
    private void personalProductsIntents() {
        if (getIntent().hasExtra("phone") && getIntent().hasExtra("name") && getIntent().hasExtra("location") &&
                getIntent().hasExtra("mail") && getIntent().hasExtra("image") && getIntent().hasExtra("speciality") ) {
            String pName, pLocation, pMail, pImage, pPhone, pSpeciality;
            pPhone = getIntent().getStringExtra("phone");
            pName = getIntent().getStringExtra("name");
            pLocation = getIntent().getStringExtra("location");
            pMail = getIntent().getStringExtra("mail");
            pImage = getIntent().getStringExtra("image");
            pSpeciality = getIntent().getStringExtra("speciality");



            tvName.setText("Name :            " + pName);
            tvMail.setText("E-Mail Address :         " + pMail);
            tvPhone.setText("Phone Number :         " + pPhone);
            tvLocation.setText("Location :          " + pLocation);
            tvSpeciality.setText("Specialization :      " + pSpeciality);

            Picasso.get().load(pImage).placeholder(R.drawable.ic_image_black_24dp).into(profileImage);
            Toast.makeText(this, "Saving Profile SuccessFull...", Toast.LENGTH_SHORT).show();

        }
    }
}
