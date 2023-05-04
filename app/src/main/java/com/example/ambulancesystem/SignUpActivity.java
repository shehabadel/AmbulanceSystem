package com.example.ambulancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView proceedButton;
    private EditText firstName, lastName, email, password, confirmPassword;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backButton = (ImageView) findViewById(R.id.backButton);
        proceedButton = (TextView) findViewById(R.id.proceedButton);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void createAccount(){
        String firstNameInput = firstName.getText().toString();
        String lastNameInput = lastName.getText().toString();
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();
        String confirmPasswordInput = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(firstNameInput)){
            firstName.setError("Enter your first name");
            firstName.requestFocus();
        }
        else if (TextUtils.isEmpty(lastNameInput)){
            lastName.setError("Enter your last name");
            lastName.requestFocus();
        }
        else if (TextUtils.isEmpty(emailInput)){
            email.setError("Enter your email address");
            email.requestFocus();
        }
        else if(!isEmailValid(emailInput)){
            email.setError("Email address format in invalid");
            email.requestFocus();
        }
        else if (TextUtils.isEmpty(passwordInput)){
            password.setError("Enter your password");
            password.requestFocus();
        }
        else if(passwordInput.length() < 6){
            password.setError("Password must be more than 6 digits");
            password.requestFocus();
        }
        else if(!passwordInput.equals(confirmPasswordInput)){
            confirmPassword.setError("Password doesn't match");
            confirmPassword.requestFocus();
        }
        else{
            loadingBar.setTitle("Register");
            loadingBar.setMessage("Account is being created, just few seconds.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validate(firstNameInput, lastNameInput, emailInput, passwordInput);
        }
    }

    private void validate(String firstNameInput, String lastNameInput, String emailInput, String passwordInput){

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ambucare-5d143-default-rtdb.firebaseio.com/");
                            DatabaseReference myRef = database.getReference();
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    HashMap<String, Object> userDataMap = new HashMap<>();
                                    userDataMap.put("email", emailInput);
                                    userDataMap.put("firstName", firstNameInput);
                                    userDataMap.put("lastName", lastNameInput);

                                    myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userDataMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(SignUpActivity.this, "Your account has been created successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                                        startActivity(intent);
                                                    }
                                                    else{
                                                        loadingBar.dismiss();
                                                        Toast.makeText(SignUpActivity.this, "Error! Please try again", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    loadingBar.dismiss();
                                    Log.d("Failure", "Database failure");
                                }
                            });
                        } else{
                            loadingBar.dismiss();
                            Toast.makeText(SignUpActivity.this, "Email already Exists, or Password less than 6 digits", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

