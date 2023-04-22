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

import com.example.ambulancesystem.Models.Address;
import com.example.ambulancesystem.Models.RequestModel;
import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.ViewModels.RequestViewModel;
import com.example.ambulancesystem.ViewModels.UserViewModel;

public class SignInActivity extends AppCompatActivity {

    ImageView backButton;
    TextView signUpButton;
    TextView signInButton;
    UserViewModel userViewModel;

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
                //userViewModel.updateUserAddress(new Address("alo","alo street",12,32,1));
//                userViewModel.updateProfile(
//                        new UserModel("Omar", "ssss", "a7a", "1999-23-1", "01000128323", "Male", "273661618121",
//                        new Address("cairo", "street1", 17, 19, 22))
//                );
                Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}