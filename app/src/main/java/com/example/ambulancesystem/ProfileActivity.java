package com.example.ambulancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.ViewModels.UserViewModel;
import com.example.ambulancesystem.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

//    private ImageView backButton;
//    private ImageView logoutButton;
//    private RelativeLayout homeButton;
    private ActivityProfileBinding binding;
    UserModel user;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        backButton = (ImageView) findViewById(R.id.backButton);
//        logoutButton = findViewById(R.id.logoutButton);
//        homeButton = findViewById(R.id.homeButtonProfile);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init();
        userViewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                user = new UserModel(userModel, true);
                binding.firstName.setText(user.getFirstName() != null ? user.getFirstName().toString() : "");
                binding.lastName.setText(user.getLastName() != null ? user.getLastName().toString() : "");
                binding.medicalCondition.setText(user.getMedicalCondition() != null ? user.getMedicalCondition().toString() : "");
                binding.nationalID.setText(user.getNationalID() != null ? user.getNationalID().toString() : "");
                binding.profileEmail.setText(user.getEmail() != null ? user.getEmail().toString() : "");
                binding.profilePhone.setText(user.getPhoneNumber() != null ? user.getPhoneNumber().toString() : "");
                binding.profileBirthDate.setText(user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "");
            }
        });

        binding.homeButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Homepage.class);
                startActivity(intent);
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Homepage.class);
                startActivity(intent);
             }
        });





    }
}