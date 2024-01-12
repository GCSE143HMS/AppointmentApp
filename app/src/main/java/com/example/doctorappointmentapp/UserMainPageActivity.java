package com.example.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserMainPageActivity extends AppCompatActivity {
    private Button userviewappointmentsbtn, userviewprescriptionbtn, userviewdoctorsbtn, logoutbtn, userbookappointmentbtn;
    private String pname, pgender,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);

        userbookappointmentbtn=(Button) findViewById(R.id.userbookappointmentbtn);
        userviewappointmentsbtn=(Button) findViewById(R.id.userviewAppointmentsBtn);
        userviewdoctorsbtn =(Button) findViewById(R.id.userviewDoctorsBtn);
        //userviewprescriptionbtn=(Button) findViewById(R.id.userviewPrescriptionBtn);
        logoutbtn=(Button) findViewById(R.id.gobackbtn);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        pname = intent.getStringExtra("pname");
        pgender = intent.getStringExtra("pgender");

        userbookappointmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserBookAppointmentsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("pname", pname);
                intent.putExtra("pgender", pgender);
                startActivity(intent);
            }
        });
        userviewappointmentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserViewAppointmentsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("pname", pname);
                intent.putExtra("pgender", pgender);
                startActivity(intent);
            }
        });
        /*
        userviewprescriptionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserViewPrescriptionActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("pname", pname);
                intent.putExtra("pgender", pgender);
                startActivity(intent);
            }
        });
        */
        userviewdoctorsbtn.setOnClickListener(new View.OnClickListener() {
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