package com.example.ambulancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MedicalRecordActivity extends AppCompatActivity {

    private RadioGroup genderRadioGroup;
    private RadioButton maleButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        maleButton = (RadioButton) findViewById(R.id.maleRadioButton);
        backButton = (ImageView) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicalRecordActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        maleButton.setChecked(true);
        maleButton.setBackgroundResource(R.drawable.checked);

        // add onCheckedChangeListener to the radio group
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
    }
}