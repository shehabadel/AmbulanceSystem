package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.ETAResponse;
import com.example.ambulancesystem.Models.HospitalModel;
import com.google.firebase.auth.FirebaseAuth;

public class MapsActivity2 extends AppCompatActivity {
    TextView lat;
    TextView lang;
    ImageButton backbtn;
    ImageView avatar;
    TextView cancelRequestButton;
    RelativeLayout homeButton;
    RelativeLayout accountButton;
    ImageView logoutButton;
    TextView hospitalName;
    TextView hospitalLocationLong;
    TextView hospitalLocationLat;
    TextView driverCarNumber;
    TextView driverName;
    TextView etaResponseTV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_text);
        lat = (TextView) findViewById(R.id.lat);
        lang = (TextView) findViewById(R.id.lang);
        hospitalLocationLong = (TextView) findViewById(R.id.hospitalLocationLong);
        hospitalLocationLat = (TextView) findViewById(R.id.hospitalLocationLat);
        hospitalName = (TextView) findViewById(R.id.hospitalName);
        driverCarNumber = (TextView) findViewById(R.id.driverCarNumber);
        driverName = (TextView) findViewById(R.id.driverName);
        etaResponseTV = (TextView) findViewById(R.id.etaResponse);

        DriverModel requestedDriver = getIntent().getParcelableExtra("requestedDriver");
        HospitalModel requestedHospital = getIntent().getParcelableExtra("requestedHospital");
        ETAResponse etaResponse = getIntent().getParcelableExtra("etaResponse");
        Log.d("MapsActivityDriver",requestedDriver.toString());
        hospitalLocationLong.setText(String.valueOf(requestedHospital.getHospitalLocationLong()));
        hospitalLocationLat.setText(String.valueOf(requestedHospital.getHospitalLocationLat()));
        hospitalName.setText(requestedHospital.getHospitalName());
        driverCarNumber.setText(requestedDriver.getDriveCarNumber());
        driverName.setText(requestedDriver.getDriverName());
        etaResponseTV.setText(etaResponse.getEstimatedTime());
        cancelRequestButton = findViewById(R.id.cancelRequestButton);
        cancelRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: cancel request from database
                Intent intent = new Intent(MapsActivity2.this, Homepage.class);
                startActivity(intent);
            }
        });

        logoutButton = findViewById(R.id.logoutButtonRequest);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MapsActivity2.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        homeButton = findViewById(R.id.homeButtonRequest);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity2.this, Homepage.class);
                startActivity(intent);
            }
        });

        accountButton = findViewById(R.id.accountButtonRequest);

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity2.this, Homepage.class);
                startActivity(intent);
            }
        });

        backbtn = findViewById(R.id.backButton);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity2.this, Homepage.class);
                startActivity(intent);
            }
        });
//        avatar = findViewById(R.id.avatar_image);
//        avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MapsActivity2.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}