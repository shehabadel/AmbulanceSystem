package com.example.ambulancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.ViewModels.UserViewModel;
import com.example.ambulancesystem.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

//    private ImageView backButton;
//    private ImageView logoutButton;
//    private RelativeLayout homeButton;
    private ActivityProfileBinding binding;
    UserModel user;
    UserViewModel userViewModel;
    private TextView saveProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        saveProfileButton = findViewById(R.id.saveProfileButton);

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

        binding.saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the user object with the new data
                user.setFirstName(binding.firstName.getText().toString());
                user.setLastName(binding.lastName.getText().toString());
                user.setMedicalCondition(binding.medicalCondition.getText().toString());
                user.setNationalID(binding.nationalID.getText().toString());
                user.setPhoneNumber(binding.profilePhone.getText().toString());
                user.setDateOfBirth(binding.profileBirthDate.getText().toString());

                // Update the email in Firebase Authentication
                String newEmail = binding.profileEmail.getText().toString();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    // Check if the new email is already used by another account
                    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(newEmail)
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        SignInMethodQueryResult result = task.getResult();
                                        if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                                            // Email is already used by another account
                                            List<String> signInMethods = result.getSignInMethods();
                                            boolean isExistingEmailOtherThanUser = signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)
                                                    && !firebaseUser.getEmail().equals(newEmail);
                                            if (isExistingEmailOtherThanUser) {
                                                binding.profileEmail.setError("Email address is already in use");
                                                binding.profileEmail.requestFocus();
                                                return;
                                            }
                                        }

                                        // Update the email
                                        firebaseUser.updateEmail(newEmail)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Email update successful
                                                            Toast.makeText(ProfileActivity.this, "Email updated successfully", Toast.LENGTH_SHORT).show();

                                                            // Save the updated user data to Firebase Realtime Database
                                                            String userId = firebaseUser.getUid(); // Retrieve the user ID
                                                            DatabaseReference userRef = usersRef.child(userId).child("profile");
                                                            Map<String, Object> profileUpdates = new HashMap<>();
                                                            profileUpdates.put("email", newEmail);
                                                            profileUpdates.put("firstName", user.getFirstName());
                                                            profileUpdates.put("lastName", user.getLastName());
                                                            profileUpdates.put("medicalCondition", user.getMedicalCondition());
                                                            profileUpdates.put("nationalID", user.getNationalID());
                                                            profileUpdates.put("phoneNumber", user.getPhoneNumber());
                                                            profileUpdates.put("dateOfBirth", user.getDateOfBirth());
                                                            userRef.updateChildren(profileUpdates)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                // Realtime Database update successful
                                                                                Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                // Realtime Database update failed
                                                                                Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        } else {
                                                            // Email update failed
                                                            Toast.makeText(ProfileActivity.this, "Failed to update email", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        // Failed to fetch sign-in methods
                                        Toast.makeText(ProfileActivity.this, "Failed to check email availability", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // User is not logged in
                    Toast.makeText(ProfileActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}