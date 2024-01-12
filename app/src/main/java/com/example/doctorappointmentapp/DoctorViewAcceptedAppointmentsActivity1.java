package com.example.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorViewAcceptedAppointmentsActivity1 extends AppCompatActivity{
private Button button, prescriptionbtn,  gobackbtn, saveimagebtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView txtDate, txtTime, txtName, txtGender, txtStatus, txtQuali, txtSpecia;
    private String doctorId, docName, status , specia, gender, phNum, userId,appId, appDate, appTime, patientName, pgender;
    private FirebaseFirestore db;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "StoreImageActivity";
    private Uri selectedImageUri;
    private String imageType, imageUrl;
    private ImageView imageView;

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                Log.d("Path : ", selectedImageUri.getPath());
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void uploadImage()
    {
        try {
            imageUrl="";
            // Get a reference to the Firebase Storage instance.
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
// Choose a name for your file. Replace the extension to the type of file you're uploading.
            String imageName = UUID.randomUUID()+".jpg";
// Get a reference to the location where you want to save the image.
// This is somewhat similar to how our files are saved in our PC.
// The "images" represents the folder name, you can specify it however you want.
            StorageReference imageRef = storageRef.child("images/" + imageName);
// Get the file's Uri.
// Upload the image.
            Log.d("File Path : ", selectedImageUri.getPath());
            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
// Monitor the upload progress.
            uploadTask.addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                // This listener will provide the upload status of the file.
                // You can add a progress bar in you app and set max of the progress bar to taskSnapshot.getTotalByteCount().
                // And process of the progress bar to taskSnapshot.getBytesTransferred().
            }).addOnSuccessListener(taskSnapshot -> {
                // This listener is triggered when the file is uploaded successfully.
                // Using the below code you can get the download url of the file
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUrl = uri.toString();
                    Log.d("Image Url : ", imageUrl);
                    // Do something with the file URL, like saving it to a database or displaying it from cloud.
                    Toast
                            .makeText(getApplicationContext(),
                                    "Image Uploaded!!",
                                    Toast.LENGTH_LONG)
                            .show();
                });
            }).addOnFailureListener(exception -> {
                // This listener is triggered if there was an exception occured while uploading the file.
                // You can display a error message from here if exception occurs.
                Toast
                        .makeText(getApplicationContext(),
                                "Failed " + exception.getMessage(),
                                Toast.LENGTH_LONG).show();
            });
        }catch (Exception e)
        {
            Toast
                    .makeText(getApplicationContext(),
                            "Failed " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_accepted_appointments1);
        prescriptionbtn = (Button) findViewById(R.id.prescriptionbutton);
        saveimagebtn = (Button) findViewById(R.id.saveimagebutton);
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(saveImageInDB())
                imageChooser();
            }
        });

        saveimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                Toast.makeText(getApplicationContext(), "Prescription Saved Success", Toast.LENGTH_SHORT).show();
            }
        });

        prescriptionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("Click on New User", "New Appointment");
                    db.collection("NewAppointment").document(appId).update("imageUrl",imageUrl);
                    db.collection("NewAppointment").document(appId).update("status","PrescriptionUpdated");
                    Toast.makeText(getApplicationContext(), "Prescription Updated Success", Toast.LENGTH_SHORT).show();
                }catch(Exception e)
                {
                    Log.d("Exception : ", e.getMessage());
                }
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