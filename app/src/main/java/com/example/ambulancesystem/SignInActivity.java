package com.example.ambulancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulancesystem.Interfaces.DriverInterface;
import com.example.ambulancesystem.Models.RequestModel;
import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.Services.DriverService;
import com.example.ambulancesystem.ViewModels.RequestViewModel;
import com.example.ambulancesystem.ViewModels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    ImageView backButton;
    TextView signUpButton;
    TextView signInButton;
    UserViewModel userViewModel;
    RequestViewModel requestViewModel;
    RequestModel currentRequest;

    private EditText emailSignIn, passwordSignIn;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toast.makeText(getApplicationContext(), "Test toast message", Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();

        backButton = (ImageView) findViewById(R.id.backButton);
        signUpButton = (TextView) findViewById(R.id.signUpButton);
        signInButton = (TextView) findViewById(R.id.signInButton);
        emailSignIn = (EditText) findViewById(R.id.emailSignIn);
        passwordSignIn = (EditText) findViewById(R.id.passwordSignIn);

        loadingBar = new ProgressDialog(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init();
        userViewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                Log.d("UserModel", userModel.toString());
            }
        });

        requestViewModel = new ViewModelProvider(this).get(RequestViewModel.class);
        requestViewModel.init();
        requestViewModel.getRequest().observe(this, new Observer<RequestModel>() {
            @Override
            public void onChanged(RequestModel requestModel) {
                currentRequest=requestModel;
                if(currentRequest!=null){
                    Log.d("currentRequest",currentRequest.toString());
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
//                requestViewModel.createRequest(new RequestModel(Status.REQUESTED, new DriverModel("asdasda", "12133218281",
//                        "12312321", "3434", new Location(906, 10330))));
//                driverService = new DriverService();
//                driverAPI = driverService.getDriverInterface();
//                if (driverAPI != null) {
//                    Call<List<DriverModel>> call = driverAPI.getDrivers();
//                    call.enqueue(new Callback<List<DriverModel>>() {
//                        @Override
//                        public void onResponse(Call<List<DriverModel>> call, Response<List<DriverModel>> response) {
//                            if(!response.isSuccessful()){
//                                Log.d("driverAPI","---Not successful");
//                            }else{
//                                List<DriverModel> allDrivers = response.body();
//                                for (DriverModel driver:allDrivers){
//                                    Log.d("Drivers","----"+driver.getDriverName());
//                                }
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<List<DriverModel>> call, Throwable t) {
//
//                        }
//                    });
//                } else {
//                    Log.d("driverAPI", "---DriverInterface is null");
//                }
//                Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
//                startActivity(intent);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void signIn() {
        String emailInput = emailSignIn.getText().toString();
        String passwordInput = passwordSignIn.getText().toString();

        if (TextUtils.isEmpty(emailInput)) {
            emailSignIn.setError("Enter your email address");
            emailSignIn.requestFocus();
        } else if (TextUtils.isEmpty(passwordInput)) {
            passwordSignIn.setError("Enter your password");
            passwordSignIn.requestFocus();
        } else if (!isEmailValid(emailInput)) {
            emailSignIn.setError("Email address format in invalid");
            emailSignIn.requestFocus();
        } else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Account credentials are being validated, just few seconds.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validateSignIn(emailInput, passwordInput);
        }
    }

    private void validateSignIn(String emailInput, String passwordInput) {
        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User signed in successfully
                            loadingBar.setTitle("Sign In");
                            loadingBar.setMessage("Signed In Successfully");
                            loadingBar.setCanceledOnTouchOutside(false);
                            loadingBar.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isFinishing() && loadingBar.isShowing()) {
                                        loadingBar.dismiss();
                                    }
                                }
                            }, 3000); // 3000 milliseconds = 3 seconds
                            Intent intent;
                            if(currentRequest!=null){
                                 intent = new Intent(SignInActivity.this, MapsActivity2.class);
                                // Pass the variables as extras in the Intent
                                intent.putExtra("requestedDriver", currentRequest.getRequestDriver());
                                intent.putExtra("requestedHospital", currentRequest.getRequestHospital());
                                intent.putExtra("etaResponse", currentRequest.getRequestETA());

                            }else{
                                intent = new Intent(SignInActivity.this, Homepage.class);
                            }
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign-in failed, show error message
                            loadingBar.setTitle("Sign In");
                            loadingBar.setMessage("Username or password incorrect");
                            loadingBar.setCanceledOnTouchOutside(false);
                            loadingBar.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isFinishing() && loadingBar.isShowing()) {
                                        loadingBar.dismiss();
                                    }
                                }
                            }, 3000); // 3000 milliseconds = 3 seconds
                        }
                    }
                });
    }
}
