package com.example.bika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);




        ///////////////////////////////////////////
        Window window = MyProfile.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MyProfile.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_my_profile);

/////////////////////////////////////////



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("Users");

        String email = EmailHolder.getUserEmail();

        usersCollection.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null && !task.getResult().isEmpty()) {

                                DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);

                                // Get user details from Firestore
                                String username = userDoc.getString("name");
                                String telephone = userDoc.getString("phoneNumber");

                                // Set retrieved details to EditText views
                                EditText userNameTextView = findViewById(R.id.name);
                                userNameTextView.setText(username);

                                EditText phoneTextView = findViewById(R.id.price);
                                phoneTextView.setText(telephone);
                            } else {
                                // Handle case where no user data is found
                            }
                        } else {
                            // Handle unsuccessful Firestore query
                        }
                    }
                });  // Fetch user details from Firestore











        Button btnUpdate = findViewById(R.id.button);

        EditText nameEditText = findViewById(R.id.name);
        EditText phoneEditText = findViewById(R.id.price);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameEditText.getText().toString();
                String userPhone = phoneEditText.getText().toString();


                usersCollection.whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null && !task.getResult().isEmpty()) {
                                        DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);
                                        String docId = userDoc.getId();


                                        usersCollection.document(docId).update("name", userName, "phoneNumber", userPhone)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Update successful
                                                            Toast.makeText(MyProfile.this, "Details updated successfully!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            // Handle unsuccessful Firestore update
                                                            Toast.makeText(MyProfile.this, "Failed to update details. Please try again.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {

                                    }
                                } else {

                                }
                            }
                        });

                // Update user details in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", userName);
                editor.putString("phoneNumber", userPhone);
                editor.apply();
            }
        });
    }
}
