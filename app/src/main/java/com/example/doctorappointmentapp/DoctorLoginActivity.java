package com.example.doctorappointmentapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DoctorLoginActivity extends AppCompatActivity {
    private Button signInBtn, goBackBtn;
    private EditText txtUname, txtPwd;
    private String username, password;
    private String userId;
private boolean flag;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
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
                    db.collection("NewDoctor").get()
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
                                            NewDoctorClass userClass = d.toObject(NewDoctorClass.class);
                                            if (userClass.getUserName().equals(username) && password.equals(userClass.getPassword())) {
                                                userId = d.getId();
                                                Log.d("UserId ", userId);
                                                flag = true;
                                                break;
                                            }
                                        }
                                        if (flag) {
                                            Toast.makeText(getApplicationContext(), "Doctor Login Success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), DoctorMainPageActivity.class);
                                            intent.putExtra("userId", userId);
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