package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.ETAResponse;
import com.example.ambulancesystem.Models.HospitalModel;
import com.example.ambulancesystem.Services.DriverService;
import com.example.ambulancesystem.Services.HospitalService;
import com.example.ambulancesystem.ViewModels.RequestViewModel;
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
    RequestViewModel requestViewModel;
    DriverService driverService;
    HospitalService hospitalService;

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
        //APIs
        requestViewModel = new ViewModelProvider(this).get(RequestViewModel.class);
        requestViewModel.init();
        //API driver Service
        driverService = new DriverService();
        //API Hospital Service
        hospitalService = new HospitalService();
        DriverModel requestedDriver = getIntent().getParcelableExtra("requestedDriver");
        HospitalModel requestedHospital = getIntent().getParcelableExtra("requestedHospital");
        ETAResponse etaResponse = getIntent().getParcelableExtra("etaResponse");
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
                requestViewModel.removeRequest();
                driverService.cancelRequest(Integer.toString(requestedDriver.getDriverID()), new DriverService.CancelRequestCallback() {
                    @Override
                    public void onSuccess(String confirmationMessage) {
                        Toast.makeText(getApplicationContext(),"Cancelled Driver successfully!",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getApplicationContext(),"Cancel driver err: "+errorMessage,Toast.LENGTH_SHORT);
                        Log.d("cancelDriverERR",errorMessage);
                    }
                });
                hospitalService.cancelRequest(Integer.toString(requestedHospital.getHospitalID()), new HospitalService.CancelRequestCallback() {
                    @Override
                    public void onSuccess(String confirmationMessage) {
                        Toast.makeText(getApplicationContext(),"Cancelled Hospital seat successfully!",Toast.LENGTH_SHORT);

                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getApplicationContext(),"Cancel hospital err: "+errorMessage,Toast.LENGTH_SHORT);
                        Log.d("cancelhospitalERR",errorMessage);
                    }
                });
                Intent intent = new Intent(MapsActivity2.this, Homepage.class);
                startActivity(intent);
                finish();
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

        accountButton = findViewById(R.id.accountButtonRequest);
//        accountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MapsActivity2.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });


    }
}