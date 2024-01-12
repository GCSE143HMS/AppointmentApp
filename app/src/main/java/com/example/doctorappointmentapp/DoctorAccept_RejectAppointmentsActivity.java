package com.example.doctorappointmentapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class DoctorAccept_RejectAppointmentsActivity extends AppCompatActivity{
private Button button, acceptbtn, rejectbtn, gobackbtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView txtDate, txtTime, txtName, txtGender, txtStatus, txtQuali, txtSpecia;
    private String doctorId, docName, status , specia, gender, phNum, userId,appId, appDate, appTime, patientName, pgender;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_accept_reject_appointments);
        acceptbtn = (Button) findViewById(R.id.acceptbutton);
        rejectbtn = (Button) findViewById(R.id.rejectbutton);
        //gobackbtn = (Button) findViewById(R.id.gobackbtn);
        txtName=(TextView) findViewById(R.id.textViewPatientName);
        txtGender=(TextView) findViewById(R.id.textViewGender);
        txtDate=(TextView) findViewById(R.id.textViewAppDate);
        txtTime=(TextView) findViewById(R.id.textViewAppTime);
        txtStatus=(TextView) findViewById(R.id.textViewStatus);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        appId = intent.getStringExtra("id");

        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("NewAppointment").document(appId).update("status","Accepted");
                Toast.makeText(getApplicationContext(), "Appointment Status Accepted", Toast.LENGTH_SHORT).show();
                txtStatus.setText("Accepted");
            }
        });

        rejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("NewAppointment").document(appId).update("status","Rejected");
                Toast.makeText(getApplicationContext(), "Appointment Status Rejected", Toast.LENGTH_SHORT).show();
                txtStatus.setText("Rejected");
            }
        });

        db.collection("NewAppointment").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            String str = "";
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                NewAppointmentClass objClass = d.toObject(NewAppointmentClass.class);
                                if(appId.equals(d.getId()))
                                {
                                    patientName=objClass.getPatientName();
                                    gender=objClass.getGender();
                                    appDate=objClass.getAppDate();
                                    appTime=objClass.getAppTime();
                                    status=objClass.getStatus();

                                    txtGender.setText(gender);
                                    txtName.setText(patientName);
                                    txtDate.setText(appDate);
                                    txtTime.setText(appTime);
                                    txtStatus.setText(status);
                                    break;
                                }
                            }
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getApplicationContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(getApplicationContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}