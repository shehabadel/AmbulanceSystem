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
        if (driverAPI != null) {
            Call<List<DriverModel>> call = driverAPI.getDrivers();
            call.enqueue(new Callback<List<DriverModel>>() {
                @Override
                public void onResponse(Call<List<DriverModel>> call, Response<List<DriverModel>> response) {
                    if (!response.isSuccessful()) {
                        Log.d("driverAPI", "---Not successful");
                    } else {
                        List<DriverModel> allDrivers = response.body();
                        for (DriverModel driver : allDrivers) {
                            Log.d("Driveaas", "----" + driver.getDriverName());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DriverModel>> call, Throwable t) {
                    Log.d("driverAPI", "---Call failed: " + t.getMessage());
                }
            });
        } else {
            Log.d("driverAPI", "---DriverInterface is null");
        }
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