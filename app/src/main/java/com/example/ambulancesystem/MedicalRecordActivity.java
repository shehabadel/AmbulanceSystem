package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MedicalRecordActivity extends AppCompatActivity {

    private RadioGroup genderRadioGroup;
    private RadioButton maleButton;
    private ImageView backButton;
    private TextView signUpButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        maleButton = (RadioButton) findViewById(R.id.maleRadioButton);
        backButton = (ImageView) findViewById(R.id.backButton);
        signUpButton = (TextView) findViewById(R.id.signUpButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicalRecordActivity.this, SignUpActivity.class);
                startActivity(intent);
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
        int height = 1200;
        boolean focusable = false;

        PopupWindow popupWindow = new PopupWindow(
                popupView, width, height, focusable);

        popupWindow.setElevation(10);

        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView confirmAddressButton = popupView.findViewById(R.id.confirmAddress);

        confirmAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicalRecordActivity.this, SignUpActivity.class);
                startActivity(intent);

                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}