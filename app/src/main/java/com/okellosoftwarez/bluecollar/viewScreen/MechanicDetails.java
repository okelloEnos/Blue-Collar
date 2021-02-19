package com.okellosoftwarez.bluecollar.viewScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.okellosoftwarez.bluecollar.R;
import com.squareup.picasso.Picasso;

public class MechanicDetails extends AppCompatActivity {
    private static final int CALL_PERMISSION = 30;
    String mechName, mechLocation, mechMail, mechImage, mechPhone, mechSpeciality;
    TextView tv_name, tv_location, tv_mail, tv_phone, tv_speciality;
    ImageView mechanicImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Worker Details");

        tv_name = findViewById(R.id.tv_detailMechName);
        tv_location = findViewById(R.id.tv_detailMechLocation);
        tv_speciality = findViewById(R.id.tv_detailMechSpeciality);
        tv_mail = findViewById(R.id.tv_detailEmail);
        tv_phone = findViewById(R.id.tv_detailMechPhone);
        mechanicImage = findViewById(R.id.detailMechImage);

        receiveIntents();
    }

    private void receiveIntents() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("location") && getIntent().hasExtra("mail")
                && getIntent().hasExtra("image") && getIntent().hasExtra("phone") && getIntent().hasExtra("speciality")) {

            mechName = getIntent().getStringExtra("name");
            mechLocation = getIntent().getStringExtra("location");
            mechMail = getIntent().getStringExtra("mail");
            mechImage = getIntent().getStringExtra("image");
            mechPhone = getIntent().getStringExtra("phone");
            mechSpeciality = getIntent().getStringExtra("speciality");

            provision(mechName, mechLocation, mechMail, mechImage, mechPhone, mechSpeciality);
        }
    }

    private void provision(String mechName, String mechLocation, String mechMail, String mechImage, String mechPhone, String speciality) {
        tv_name.setText(mechName);
        tv_location.setText(mechLocation);
        tv_mail.setText(mechMail);
        tv_phone.setText(mechPhone);
        tv_speciality.setText(speciality);

        Picasso.get().load(mechImage).placeholder(R.drawable.ic_image_black_24dp)
                .into(mechanicImage);

        Toast.makeText(this, "Url : " + mechImage, Toast.LENGTH_SHORT).show();
    }

    public void mailing(View view) {

        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mechMail});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "REQUESTING FOR MECHANIC ASSISTANCE");
        mailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(mailIntent, "Choose Mail Client"));

    }

    public void phoning(View view) {
        if (Build.VERSION.SDK_INT >= 23){
            if (checkedPermission()){
//                Permission Already Granted
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + mechPhone));
                startActivity(phoneIntent);
            }
            else {
                requestPermission();
            }
        }
        if (checkedPermission()){
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:" + mechPhone));
            startActivity(phoneIntent);
        }
    }

    private boolean checkedPermission() {
        int callPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);

        return callPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MechanicDetails.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CALL_PERMISSION :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText(this, "Permission For Calling has been Accepted...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission For Calling has been Denied...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
