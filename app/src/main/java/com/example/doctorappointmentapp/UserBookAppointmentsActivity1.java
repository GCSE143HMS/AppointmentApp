package com.example.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserBookAppointmentsActivity1 extends AppCompatActivity  implements View.OnClickListener{
private Button button, datebtn, timebtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView txtDate, txtTime, txtName, txtGender, txtPhnum, txtQuali, txtSpecia;
    private String doctorId, docName, quali , specia, gender, phNum, userId,appId, appDate, appTime, patientName, pgender;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_appointments1);
        button = (Button) findViewById(R.id.bookappbutton);
        datebtn = (Button) findViewById(R.id.datebutton);
        timebtn = (Button) findViewById(R.id.timebutton);
        txtDate=(TextView) findViewById(R.id.textDate);
        txtTime=(TextView) findViewById(R.id.textTime);
        txtName=(TextView) findViewById(R.id.textViewDocName);
        txtGender=(TextView) findViewById(R.id.textViewGender);
        txtPhnum=(TextView) findViewById(R.id.textViewDocPhNum);
        txtSpecia=(TextView) findViewById(R.id.textViewDocSpecia);
        txtQuali=(TextView) findViewById(R.id.textViewDocType);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        doctorId = intent.getStringExtra("id");
        docName = intent.getStringExtra("docname");
        quali = intent.getStringExtra("quali");
        specia = intent.getStringExtra("specia");
        gender = intent.getStringExtra("gender");
        phNum = intent.getStringExtra("phnum");
        userId = intent.getStringExtra("userId");
        pgender = intent.getStringExtra("pgender");
        patientName = intent.getStringExtra("pname");

        /*db.collection("NewDoctor").get()
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
                                NewDoctorClass objClass = d.toObject(NewDoctorClass.class);
                                if(docId.equals(objClass.getDoctorId()))
                                {
                                    docName=objClass.getFirstName()+" "+ objClass.getLastName();
                                    gender=objClass.getGender();
                                    specia=objClass.getSpecialization();
                                    quali=objClass.getQualification();
                                    phNum=objClass.getPhoneNum();
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
                });*/

        txtName.setText(docName);
        txtGender.setText(gender);
        txtQuali.setText(quali);
        txtSpecia.setText(specia);
        txtPhnum.setText(phNum);

        datebtn.setOnClickListener(this);
        timebtn.setOnClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), AppMainActivity.class);
                //startActivity(intent);
                try {
                    Log.d("Click on New User", "New Appointment");
                    appDate=txtDate.getText().toString();
                    appTime=txtTime.getText().toString();
                    if(TextUtils.isEmpty(appDate)){
                        txtDate.setError("Appointment Date is Empty");
                        txtDate.isFocusable();
                    }
                    else if(TextUtils.isEmpty(appTime)){
                        txtTime.setError("Appointment Time is Empty");
                        txtTime.isFocusable();
                    }
else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    UUID uuid = UUID.randomUUID();
                    appId = uuid.toString();

                    // Create a new user with a first and last name
                        /*
                        Map<String, Object> user = new HashMap<>();
                        user.put("userId", userId);
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.put("userName", userName);
                        user.put("password", password);
                        user.put("emailId", emailId);
                        user.put("phoneNum", phoneNum);
                        user.put("gender", gender);
                        */
                    NewAppointmentClass newClass = new NewAppointmentClass(appId, userId, doctorId, docName, phNum,
                            appDate, appTime, quali, specia, gender, patientName, pgender,"Booked","");
                    // Add a new document with a generated ID
                    db.collection("NewAppointment")
                            .add(newClass)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Success : ", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(getApplicationContext(), "New Appointment Inserted Successfully",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Error : ", "Error adding document", e);
                                    Toast.makeText(getApplicationContext(), "Error adding document",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    /*
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dbRef = database.getReference();
                        dbRef = database.getReference("NewDoctor");
                        userId = dbRef.push().getKey();
                        NewDoctorClass newClass =new NewDoctorClass(userId, firstName, lastName, gender,
                                emailId, phoneNum, userName, password, qualification, specialization);
                        dbRef.child(userId).setValue(newClass);
                        Toast.makeText(getApplicationContext(), "New Doctor Inserted Successfully",
                                Toast.LENGTH_LONG).show();
                        txtfirstName.setText("");
                        txtlastName.setText("");
                        txtphoneNum.setText("");
                        txtemailId.setText("");
                        txtphoneNum.setText("");
                    /*
                    try{
                    FileInputStream serviceAccount = new FileInputStream("key.json");
                    FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setDatabaseUrl("https://yamahaapp-75988-default-rtdb.firebaseio.com")
                            .build();
                    FirebaseApp.initializeApp(options);
                    Toast.makeText(getApplicationContext(), "New User Inserted Successfully",
                            Toast.LENGTH_LONG).show();
                    }catch (Exception ex)
                    {
                        Log.d("Exception : ", ex.getMessage());
                    }*/
                        /*CollectionReference dbCourses = db.collection("NewUser");
                        UUID uuid=UUID.randomUUID();
                        userId = uuid.toString();
                        Log.d("UserId : ", userId);
                        String key="YamahaMotors";
                        //String encData = aes.AESencrypt(key.getBytes("UTF-16LE"), password.getBytes("UTF-16LE"));
                        String encData = AESCrypt.encrypt(password);
                        // adding our data to our courses object class.
                        NewPatientClass newUserClass =new NewPatientClass(userId, firstName, lastName, gender,
                                emailId, phoneNum, userName, encData);

                        // below method is use to add data to Firebase Firestore.
                        dbCourses.add(newUserClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // after the data addition is successful
                                // we are displaying a success toast message.
                                Toast.makeText(getApplicationContext(), "User has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // this method is called when the data addition process is failed.
                                // displaying a toast message when data addition is failed.
                                Toast.makeText(getApplicationContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
                            }
                        });*/
                }
                }catch(Exception e)
                {
                    Log.d("Exception : ", e.getMessage());
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == datebtn) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            //datePickerDialog.show();
            c.add(Calendar.DATE, 3);
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DATE, -3);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }
        else if (v == timebtn) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}