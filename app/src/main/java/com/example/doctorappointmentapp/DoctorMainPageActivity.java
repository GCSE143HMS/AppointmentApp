package com.example.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorMainPageActivity extends AppCompatActivity {
    private Button doctorviewAppointmentsBtn, doctorviewacceptedappointmentbtn, doctorviewrejectedappointmentbtn, logoutbtn, doctorviewPrescriptionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_page);

        doctorviewPrescriptionBtn=(Button) findViewById(R.id.doctorviewPrescriptionBtn);
        doctorviewAppointmentsBtn=(Button) findViewById(R.id.doctorviewAppointmentsBtn);
        doctorviewrejectedappointmentbtn =(Button) findViewById(R.id.doctorviewrejectedappointmentbtn);
        doctorviewacceptedappointmentbtn=(Button) findViewById(R.id.doctorviewacceptedappointmentbtn);
        logoutbtn=(Button) findViewById(R.id.gobackbtn);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        doctorviewAppointmentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorViewAppointmentsActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        doctorviewPrescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserViewAppointmentsActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        doctorviewrejectedappointmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserViewPrescriptionActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        doctorviewacceptedappointmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserViewDoctorsActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}