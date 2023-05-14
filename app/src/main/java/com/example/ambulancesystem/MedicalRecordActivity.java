package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.ViewModels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MedicalRecordActivity extends AppCompatActivity {

    private RadioGroup genderRadioGroup;
    private RadioButton maleButton;
    private ImageView backButton;
    private TextView signUpButton;
    private TextView phoneNumberTextView;
    private TextView nationalIDTextView;
    private TextView dateOfBirthTextView;
    private TextView medicalConditionTextView;
    FirebaseAuth auth;

    UserModel user;
    UserViewModel userViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);
        auth = FirebaseAuth.getInstance();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init();
        userViewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                user = new UserModel(userModel, true);
                phoneNumberTextView.setText(user.getPhoneNumber() != null ? user.getPhoneNumber().toString() : "");
                nationalIDTextView.setText(user.getNationalID() != null ? user.getNationalID().toString() : "");
                dateOfBirthTextView.setText(user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "");
                medicalConditionTextView.setText(user.getMedicalCondition() != null ? user.getMedicalCondition().toString() : "");
            }
        });
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        maleButton = (RadioButton) findViewById(R.id.maleRadioButton);
        backButton = (ImageView) findViewById(R.id.backButton);
        signUpButton = (TextView) findViewById(R.id.signUpButton);
        phoneNumberTextView = (TextView) findViewById(R.id.phoneNumber);
        nationalIDTextView = (TextView) findViewById(R.id.nationalID);
        dateOfBirthTextView = (TextView) findViewById(R.id.birthDate);
        medicalConditionTextView = (TextView) findViewById(R.id.medicalCondition);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MedicalRecordActivity.this, MainActivity.class));
                finish();
            }
        });

        maleButton.setChecked(true);
        maleButton.setBackgroundResource(R.drawable.checked);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                if (checkedRadioButton != null) {
                    // change the background of the checked radio button
                    checkedRadioButton.setBackgroundResource(R.drawable.checked);
                }
                // uncheck all other radio buttons
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton != checkedRadioButton) {
                        radioButton.setChecked(false);
                        radioButton.setBackgroundResource(R.drawable.unchecked);
                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String gender = radioButton.getText().toString();
                String phoneNumber = (String) phoneNumberTextView.getText().toString();
                String nationalID = (String) nationalIDTextView.getText().toString();
                String dateOfBirth = (String) dateOfBirthTextView.getText().toString();
                String medicalCondition = (String) medicalConditionTextView.getText().toString();
                Log.d("medicalRecord::", gender);
                Log.d("medicalRecord::", phoneNumber);
                Log.d("medicalRecord::", nationalID);
                Log.d("medicalRecord::", dateOfBirth);
                Log.d("medicalRecord::", medicalCondition);
                UserModel userDetails = new UserModel(medicalCondition, dateOfBirth, phoneNumber, gender, nationalID);
                userViewModel.updateProfile(userDetails);
                showCongratsPopup();
            }
        });

    }

    private void showCongratsPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.congrats_popup, null);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f; // set the transparency here
        params.dimAmount = 0.5f;
        getWindow().setAttributes(params);


        int width = 789;
        int height = 1032;
        boolean focusable = false;

        PopupWindow popupWindow = new PopupWindow(
                popupView, width, height, focusable);

        popupWindow.setElevation(10);

        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);

        TextView startButton = popupView.findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                showAddressPopup();
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void showAddressPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.saved_address_popup, null);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f; // set the transparency here
        params.dimAmount = 0.5f;
        getWindow().setAttributes(params);


        int width = 1000;
        int height = 1400;
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(
                popupView, width, height, focusable);

        popupWindow.setElevation(10);

        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView confirmAddressButton = popupView.findViewById(R.id.confirmAddress);

        confirmAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Save the pickup location in the database
                popupWindow.dismiss();
                Intent intent = new Intent(MedicalRecordActivity.this, Homepage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}