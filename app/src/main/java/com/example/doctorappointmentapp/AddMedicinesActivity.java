package com.example.doctorappointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.io.FileInputStream;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMedicinesActivity extends AppCompatActivity {

    private EditText txtMedicineName, txtQty, txtPrice, txtDescription;
    private String medId, medName, qty, price, medtype, description;
    private Spinner medtypeSpinner;
    private Button newmedbtn, gobackbtn;
    private String[] medicinedTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);

        txtMedicineName=(EditText)findViewById(R.id.editMedicinesname);
        txtQty=(EditText)findViewById(R.id.editMedicinesqty);
        txtPrice=(EditText)findViewById(R.id.editMedicinesprice);
        txtDescription=(EditText)findViewById(R.id.editMedicineDescription);
        medtypeSpinner = (Spinner) findViewById(R.id.medicinestypespinner);
        gobackbtn = (Button) findViewById(R.id.gobackBtn);
        newmedbtn = (Button) findViewById(R.id.addMedicinesBtn);

        medicinedTypes = getResources().getStringArray(R.array.medicinetypes);
        ArrayAdapter<String> qualiadapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, medicinedTypes);
        medtypeSpinner.setAdapter(qualiadapter);

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminMainPageActivity.class);
                startActivity(intent);
            }
        });

        newmedbtn.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                medName=txtMedicineName.getText().toString();
                qty=txtQty.getText().toString();
                price=txtPrice.getText().toString();
                description=txtDescription.getText().toString();
                medtype = medtypeSpinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(medName))
                {
                    Toast.makeText(getApplicationContext(), "Medicine Name is Empty", Toast.LENGTH_SHORT).show();
                    txtMedicineName.setFocusable(true);
                }
                else if(TextUtils.isEmpty(medtype))
                {
                    Toast.makeText(getApplicationContext(), "Medicine Type is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(qty))
                {
                    Toast.makeText(getApplicationContext(), "Quantity is Empty", Toast.LENGTH_SHORT).show();
                    txtQty.setFocusable(true);
                }
                else if(TextUtils.isEmpty(price))
                {
                    Toast.makeText(getApplicationContext(), "Price is Empty", Toast.LENGTH_SHORT).show();
                    txtPrice.setFocusable(true);
                }
                else if(TextUtils.isEmpty(description))
                {
                    Toast.makeText(getApplicationContext(), "Description is Empty", Toast.LENGTH_SHORT).show();
                    txtDescription.setFocusable(true);
                }
                else{
                    try {
                        Log.d("Click on New Medicinies", "New Medicines");
                        /*
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dbRef = database.getReference();
                        dbRef = database.getReference("NewMedicine");
                        medId = dbRef.push().getKey();
                        NewMedicineClass newClass =new NewMedicineClass(medId, medName,  qty,  price,  description, medtype);
                        dbRef.child(medId).setValue(newClass);
                        Toast.makeText(getApplicationContext(), "New Medicine Inserted Successfully",
                                Toast.LENGTH_LONG).show();
                        txtMedicineName.setText("");
                        txtQty.setText("");
                        txtPrice.setText("");
                        txtDescription.setText("");
                        */
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        UUID uuid=UUID.randomUUID();
                        medId = uuid.toString();
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
                        NewMedicineClass newClass =new NewMedicineClass(medId, medName,  qty,  price,  description, medtype);
                        // Add a new document with a generated ID
                        db.collection("NewMedicine")
                                .add(newClass)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("Success : ", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(getApplicationContext(), "New Medicine Inserted Successfully",
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
                    }catch(Exception e)
                    {
                        Log.d("Exception : ", e.getMessage());
                    }
                }
            }
        });
    }
}