package com.example.doctorappointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppMainActivity extends AppCompatActivity {

    private Button patloginbtn, docloginbtn, newpatbtn, exitbtn,adminloginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        patloginbtn=(Button) findViewById(R.id.patientloginbtn);
        newpatbtn=(Button) findViewById(R.id.newpatientbtn);
        exitbtn=(Button) findViewById(R.id.exitbtn);
        docloginbtn=(Button) findViewById(R.id.doctorloginbtn);
        adminloginbtn=(Button) findViewById(R.id.adminloginbtn);

        adminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        patloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientLoginActivity.class);
                startActivity(intent);
            }
        });

        docloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorLoginActivity.class);
                startActivity(intent);
            }
        });

        newpatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPatientActivity.class);
                startActivity(intent);
            }
        });


    }
}