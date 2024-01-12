package com.example.doctorappointmentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.UUID;
import com.squareup.picasso.Picasso;
public class UserViewPrescriptionActivity1 extends AppCompatActivity{
private Button button, gobackbtn;
    private TextView txtDate, txtTime, txtName, txtGender, txtStatus, txtQuali, txtSpecia;
    private String doctorId, docName, status , specia, gender, phNum, userId,appId, appDate, appTime, patientName, pgender;
    private FirebaseFirestore db;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "StoreImageActivity";
    private Uri selectedImageUri;
    private String imageType, imageUrl;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_accepted_appointments1);
        //gobackbtn = (Button) findViewById(R.id.gobackbtn);
        txtName=(TextView) findViewById(R.id.textViewPatientName);
        txtGender=(TextView) findViewById(R.id.textViewGender);
        txtDate=(TextView) findViewById(R.id.textViewAppDate);
        txtTime=(TextView) findViewById(R.id.textViewAppTime);
        txtStatus=(TextView) findViewById(R.id.textViewStatus);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        appId = intent.getStringExtra("id");

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
                                    try {
                                        //URL url = new URL(imageUrl);
                                        //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        //birthCertificatePicImage.setImageBitmap(bmp);
                                        Picasso.get().load(imageUrl).into(imageView);
                                    }catch (Exception e)
                                    {
                                        Log.d("Exception : ", e.getMessage());
                                    }
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