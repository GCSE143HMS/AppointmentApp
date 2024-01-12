package com.example.doctorappointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainPageActivity extends AppCompatActivity {
    private Button adminviewpatientsbtn, adminviewmedicinesbtn, newdocbtn, adminviewdoctorsbtn, logoutbtn,addmedicinesbtn,
            adminviewappointmentsbtn, newstaffbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        addmedicinesbtn=(Button) findViewById(R.id.addMedicinesBtn);
        adminviewdoctorsbtn=(Button) findViewById(R.id.viewDoctorsBtn);
        adminviewmedicinesbtn=(Button) findViewById(R.id.viewMedicinesBtn);
        logoutbtn=(Button) findViewById(R.id.logoutBtn);
        newdocbtn=(Button) findViewById(R.id.newdoctorbtn);
        adminviewpatientsbtn=(Button) findViewById(R.id.viewPatientsBtn);
        adminviewappointmentsbtn=(Button) findViewById(R.id.viewAppointmentsBtn);

        newdocbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewDoctorActivity.class);
                startActivity(intent);
            }
        });

        adminviewappointmentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewAppointmentsActivity.class);
                startActivity(intent);
            }
        });

        addmedicinesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMedicinesActivity.class);
                startActivity(intent);
            }
        });
        adminviewdoctorsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewDoctorsActivity.class);
                startActivity(intent);
            }
        });
        adminviewmedicinesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewMedicinesActivity.class);
                startActivity(intent);
            }
        });
        adminviewpatientsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewPatientsActivity.class);
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