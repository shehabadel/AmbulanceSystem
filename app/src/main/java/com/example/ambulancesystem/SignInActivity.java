package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ambulancesystem.Interfaces.DriverInterface;
import com.example.ambulancesystem.Models.Address;
import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.Location;
import com.example.ambulancesystem.Models.RequestModel;
import com.example.ambulancesystem.Models.Status;
import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.Services.DriverService;
import com.example.ambulancesystem.ViewModels.RequestViewModel;
import com.example.ambulancesystem.ViewModels.UserViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ImageView backButton;
    TextView signUpButton;
    TextView signInButton;
    UserViewModel userViewModel;
    RequestViewModel requestViewModel;
    DriverInterface driverAPI;
    DriverService driverService;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        backButton = (ImageView) findViewById(R.id.backButton);
        signUpButton = (TextView) findViewById(R.id.signUpButton);
        signInButton = (TextView) findViewById(R.id.signInButton);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init();
        userViewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                Log.d("UserModel", userModel.toString());
            }
        });

        requestViewModel = new ViewModelProvider(this).get(RequestViewModel.class);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                requestViewModel.createRequest(new RequestModel(Status.REQUESTED, new DriverModel("asdasda", "12133218281",
//                        "12312321", "3434", new Location(906, 10330))));
                driverService = new DriverService();
                driverAPI = driverService.getDriverInterface();
                if (driverAPI != null) {
                    Call<List<DriverModel>> call = driverAPI.getDrivers();
                    call.enqueue(new Callback<List<DriverModel>>() {
                        @Override
                        public void onResponse(Call<List<DriverModel>> call, Response<List<DriverModel>> response) {
                            if(!response.isSuccessful()){
                                Log.d("driverAPI","---Not successful");
                            }else{
                                List<DriverModel> allDrivers = response.body();
                                for (DriverModel driver:allDrivers){
                                    Log.d("Drivers","----"+driver.getDriverName());
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<List<DriverModel>> call, Throwable t) {

                        }
                    });
                } else {
                    Log.d("driverAPI", "---DriverInterface is null");
                }
                Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
