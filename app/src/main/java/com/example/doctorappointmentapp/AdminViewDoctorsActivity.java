package com.example.doctorappointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class AdminViewDoctorsActivity extends AppCompatActivity {
    private Button viewBtn;
    private ListView list_view;
    private String[] data,array;
    private ArrayList<String> dataArray;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_doctors);

        list_view = (ListView) findViewById(R.id.list_view);
        viewBtn = (Button) findViewById(R.id.viewBtn);
        db = FirebaseFirestore.getInstance();
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("NewDoctor").get()
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
                                        String ShowDataString = "";
                                        ShowDataString = "Doctor Name : " + objClass.getFirstName() + " " +
                                                objClass.getLastName() +
                                                "\nPhone Num : " + objClass.getPhoneNum() +
                                                "\nEmail Id : " + objClass.getEmailId()+
                                                "\nQualification : " + objClass.getQualification()+
                                                "\nSpecialization : " + objClass.getSpecialization();
                                        if (str.length() == 0)
                                            str = str + ShowDataString;
                                        else
                                            str = str + "," + ShowDataString;
                                    }
                                    array = str.split(",");
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, android.R.id.text1, array);
                                    list_view.setAdapter(adapter);
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
                // Adding addValueEventListener method on firebase object.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("NewDoctor");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@Nonnull DataSnapshot snapshot) {
                        String str = "";
                        for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                            NewDoctorClass objClass = SubSnapshot.getValue(NewDoctorClass.class);
                            String Id = objClass.getDoctorId();
                            String ShowDataString = "";

                            ShowDataString = "Doctor Name : " + objClass.getFirstName() + " " +
                                    objClass.getLastName() +
                                    "\nPhone Num : " + objClass.getPhoneNum() +
                                    "\nEmail Id : " + objClass.getEmailId()+
                                    "\nQualification : " + objClass.getQualification()+
                                    "\nSpecialization : " + objClass.getSpecialization();

                            if (str.length() == 0)
                                str = str + ShowDataString;
                            else
                                str = str + "," + ShowDataString;

                        }
                        Log.d("Data : ", str);
                        //ShowDataTextView.setText(str);
                        array = str.split(",");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1, array);
                        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);
                        list_view.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@Nonnull DatabaseError error) {
                        System.out.println("Data Access Failed" + error.getMessage());
                    }
                });*/
            }
        });
    }
}