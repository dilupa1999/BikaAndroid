package com.example.bika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Singup extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);



        ///////////////////////////////////////////
        Window window =Singup.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Singup.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_singup);

/////////////////////////////////////////





        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText Fname = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phone);
        EditText password = findViewById(R.id.editTextTextPassword2);


        Button button = (Button) findViewById(R.id.signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference UsersCollection = db.collection("Users");
                String Name = Fname.getText().toString();
                String Email = email.getText().toString();
                String Phone = phone.getText().toString();
                String Password = password.getText().toString();


                //validation
                if (Name.isEmpty()) {
                    Toast.makeText(Singup.this, "Please Enter First name", Toast.LENGTH_SHORT).show();
                } else if (Email.isEmpty()) {
                    Toast.makeText(Singup.this, "Please Enter Emial", Toast.LENGTH_SHORT).show();
                } else if (Phone.isEmpty()) {
                    Toast.makeText(Singup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();

                } else if (!isValidEmail(Email)) {
                    Toast.makeText(Singup.this, "Email is not valid", Toast.LENGTH_SHORT).show();

                } else if (Password.isEmpty()) {
                    Toast.makeText(Singup.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(Password)) {
                    Toast.makeText(Singup.this, "Password must be one upper case one lower case and letters numbers and symbols", Toast.LENGTH_SHORT).show();

                } else {


                    UsersCollection.whereEqualTo("email", Email)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null && !task.getResult().isEmpty()) {
                                        Toast.makeText(Singup.this, "Email already exist", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Map<String, Object> Users = new HashMap<>();

                                        Users.put("email", Email);
                                        Users.put("name", Name);
                                        Users.put("password", Password);
                                        Users.put("phoneNumber", Phone);




                                        //pass data to home activity
                                        UsersCollection.add(Users)
                                                .addOnSuccessListener(documentReference -> {
//                                                    fname.setText("");
//                                                    lname.setText("");
//                                                    Email1.setText("");
//                                                    Password1.setText("");
                                                    Toast.makeText(Singup.this, "Successfull", Toast.LENGTH_SHORT).show();

                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(Singup.this, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                } else {
                                    Toast.makeText(Singup.this, "Error checking email" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }


            }


            //email validation
            private boolean isValidEmail(String email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }

            // Password validation
            private boolean isValidPassword(String password) {

                String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

                return password.matches(passwordPattern);
            }


        });





                 TextView loginText =   findViewById(R.id.textView54);

                 loginText.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         startActivity(new Intent(Singup.this,Login.class));
                     }
                 });





    }

}