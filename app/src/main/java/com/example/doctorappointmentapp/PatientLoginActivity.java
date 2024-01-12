package com.example.doctorappointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.SecureRandom;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import android.os.Bundle;
import android.text.InputType;

import android.content.Context;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.nio.charset.StandardCharsets;
import java.security.*;
public class PatientLoginActivity extends AppCompatActivity {
    private Button signInBtn, goBackBtn;
    private EditText txtUname, txtPwd;
    private String username, password;
    private String userId, patientName, age, gender;
private boolean flag;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        signInBtn = (Button) findViewById(R.id.loginBtn);
        goBackBtn = (Button) findViewById(R.id.gobackBtn);
        txtUname = (EditText) findViewById(R.id.editTextUname);
        txtPwd = (EditText) findViewById(R.id.editTextPassword);

        //txtUname.setText("aaa");
        //txtPwd.setText("1234");
        db = FirebaseFirestore.getInstance();

        /*signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txtUname.getText().toString();
                password = txtPwd.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    txtUname.setError("User Name is Empty");
                    txtUname.setFocusable(true);
                } else if (TextUtils.isEmpty(password)) {
                    txtPwd.setError("Password is Empty");
                    txtPwd.setFocusable(true);
                } else {

                }
            }
        });*/

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txtUname.getText().toString();
                password = txtPwd.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    txtUname.setError("User Name is Empty");
                    txtUname.setFocusable(true);
                } else if (TextUtils.isEmpty(password)) {
                    txtPwd.setError("Password is Empty");
                    txtPwd.setFocusable(true);
                } else {
                    // Adding addValueEventListener method on firebase object.
                    username = txtUname.getText().toString();
                    password = txtPwd.getText().toString();
                    flag = false;
                    db.collection("NewUser").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
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
                                        String userId = "";
                                        for (DocumentSnapshot d : list) {
                                            // after getting this list we are passing
                                            // that list to our object class.
                                            NewUserClass userClass = d.toObject(NewUserClass.class);
                                            String decData = null;
                                            try {
                                                decData = AESCrypt.decrypt(userClass.getPassword());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            if (userClass.getUserName().equals(username) && password.equals(userClass.getPassword())) {
                                                userId = d.getId();
                                                patientName= userClass.getFirstName() + " " + userClass.getLastName();
                                                gender = userClass.getGender();
                                                Log.d("UserId ", userId);
                                                flag = true;
                                                break;
                                            }
                                        }
                                        if (flag) {
                                            Toast.makeText(getApplicationContext(), "User Login Success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), UserMainPageActivity.class);
                                            intent.putExtra("userId", userId);
                                            intent.putExtra("pname", patientName);
                                            intent.putExtra("pgender", gender);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Invalid UserName/Password", Toast.LENGTH_SHORT).show();
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
                    /*
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("NewUser");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            flag = true;
                            for (DataSnapshot SubSnapshot : snapshot.getChildren()) {
                                NewUserClass userClass = SubSnapshot.getValue(NewUserClass.class);
                                if (userClass.getUserName().equals(username)
                                        && userClass.getPassword().equals(password)) {
                                    userId = userClass.getUserId();
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                Intent intent = new Intent(getApplicationContext(), UserMainPageActivity.class);
                                intent.putExtra("userid", userId);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("Data Access Failed" + error.getMessage());
                        }
                    });*/
                }
            }
        });


        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}