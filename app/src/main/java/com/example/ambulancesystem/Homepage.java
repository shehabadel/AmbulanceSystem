package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    Switch emergencySwitch;
    TextView requestAmbulanceButton;
    ImageButton logoutButton;
    RelativeLayout accountButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_home_page);

        emergencySwitch = findViewById(R.id.emergencySwitch);
        requestAmbulanceButton = findViewById(R.id.requestAmbulanceButton);
        logoutButton = findViewById(R.id.logoutButton);
        accountButton = findViewById(R.id.accountButtonHome);

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Homepage.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        requestAmbulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMedicalCasePopup();
//                Intent intent = new Intent(Homepage.this, MapsActivity2.class);
//                startActivity(intent);
            }
        });

        emergencySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    showEmergencyPopup();
                }
            }
        });
    }
    private void showMedicalCasePopup(){
        View popupView = getLayoutInflater().inflate(R.layout.medical_case_popup, null);

        int width = 1000;
        int height = 1400;
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(
                popupView, width, height, focusable);

        popupWindow.setElevation(10);

        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);

        TextView requestMedicalCase = popupView.findViewById(R.id.requestMedicalCase);
        TextView cancelMedicalCase = popupView.findViewById(R.id.cancelMedicalCase);

        requestMedicalCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Save the pickup location in the database
                popupWindow.dismiss();
                Intent intent = new Intent(Homepage.this, MapsActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        cancelMedicalCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
    private void showEmergencyPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.emergency_popup, null);

        int width = 789;
        int height = 1032;
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(
                popupView, width, height, focusable);

        popupWindow.setElevation(10);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);

        TextView confirmEmergency = popupView.findViewById(R.id.confirmEmergencyRequest);
        TextView cancelRequest = popupView.findViewById(R.id.cancelEmergency);

        confirmEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, MapsActivity2.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                emergencySwitch.setChecked(false);
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

}