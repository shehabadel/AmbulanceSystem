package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ambulancesystem.Interfaces.DriverInterface;
import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Services.DriverService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DriverInterface driverAPI;
    DriverService driverService;
    private TextView getStartedButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        driverService = new DriverService();
        driverAPI = driverService.getDriverInterface();
        driverService.getAllDrivers(new DriverService.DriversCallback() {
            @Override
            public void onSuccess(List<DriverModel> driverModels) {
                for (DriverModel driver : driverModels) {
                    Log.d("Driveaas", "----" + driver.getDriverName());
                };
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
            }
        });

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(MainActivity.this, MedicalRecordActivity.class));
            finish();
        }
        getStartedButton = (TextView) findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}