package com.example.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class UserBookAppointmentsActivity extends AppCompatActivity {
    private Button viewBtn;
    private ListView list_view;
    private String[] data,array;
    private ArrayList<String> doctorId, doctorName, doctorPhnum, doctorGender, doctorSpecia, doctorQuali;
    private String doctor_Id, doctor_Name, doctor_Phnum, doctor_Gender, doctor_Specia, doctor_Quali,userId, pname, pgender;
    private String[] doctor_Ids, doctor_Names, doctor_Phnums, doctor_Genders, doctor_Specias, doctor_Qualis;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_appointments);

        list_view = (ListView) findViewById(R.id.list_view);
        viewBtn = (Button) findViewById(R.id.viewBtn);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        pname = intent.getStringExtra("pname");
        pgender = intent.getStringExtra("pgender");

        doctorId=doctorGender=doctorName=doctorPhnum=doctorSpecia=doctorQuali= new ArrayList<>();
        doctor_Id= doctor_Name= doctor_Phnum= doctor_Gender= doctor_Specia= doctor_Quali="";
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String docId = doctorId.get(position);
                //String docName = doctorName.get(position);
                //String quali = doctorQuali.get(position);
                //String specia = doctorSpecia.get(position);
                //String gender = doctorGender.get(position);
                //String phNum = doctorPhnum.get(position);
                String docId = doctor_Ids[position];
                String docName = doctor_Names[position];
                String quali = doctor_Qualis[position];
                String specia = doctor_Specias[position];
                String gender = doctor_Genders[position];
                String phNum = doctor_Phnums[position];
                Intent intent = new Intent(getApplicationContext(), UserBookAppointmentsActivity1.class);
                intent.putExtra("id", docId);
                intent.putExtra("docname", docName);
                intent.putExtra("quali", quali);
                intent.putExtra("specia", specia);
                intent.putExtra("gender", gender);
                intent.putExtra("phnum", phNum);
                intent.putExtra("userId", userId);
                intent.putExtra("pname", pname);
                intent.putExtra("pgender", gender);
                startActivity(intent);
            }
        });

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
                                        if (str.length() == 0) {
                                            str = str + ShowDataString;
                                        }
                                        else {
                                            str = str + "," + ShowDataString;
                                        }
                                        if(doctor_Id.length()==0)
                                        {
                                            doctor_Id=d.getId();
                                            doctor_Name=objClass.getFirstName()+" "+objClass.getLastName();
                                            doctor_Gender=objClass.getGender();
                                            doctor_Quali=objClass.getQualification();
                                            doctor_Specia=objClass.getSpecialization();
                                            doctor_Phnum=objClass.getPhoneNum();
                                        }
                                        else{
                                            doctor_Id+=","+d.getId();
                                            doctor_Name+=","+objClass.getFirstName()+" "+objClass.getLastName();
                                            doctor_Gender+=","+objClass.getGender();
                                            doctor_Quali+=","+objClass.getQualification();
                                            doctor_Specia+=","+objClass.getSpecialization();
                                            doctor_Phnum+=","+objClass.getPhoneNum();
                                        }
                                        //doctorId.add(objClass.getDoctorId());
                                        //doctorName.add(objClass.getFirstName()+" "+objClass.getLastName());
                                        //doctorSpecia.add(objClass.getSpecialization());
                                        //doctorQuali.add(objClass.getQualification());
                                        //doctorPhnum.add(objClass.getPhoneNum());
                                    }
                                    array = str.split(",");
                                    doctor_Ids = doctor_Id.split(",");
                                    doctor_Names = doctor_Name.split(",");
                                    doctor_Genders = doctor_Gender.split(",");
                                    doctor_Qualis = doctor_Quali.split(",");
                                    doctor_Specias = doctor_Specia.split(",");
                                    doctor_Phnums=doctor_Phnum.split(",");
                                    System.out.println("Doctor Ids : " + doctorId);
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