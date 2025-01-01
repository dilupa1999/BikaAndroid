package com.example.bika;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Login extends AppCompatActivity {





    private void saveUserDetails(String email, String password) {
        SharedPreferences sharedPreferences = Login.this.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ///////////////////////////////////////////
        Window window = Login.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Login.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

/////////////////////////////////////////








        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference UsersCollection = db.collection("Users");

        // Check if the user is already logged in via SharedPreferences and redirect to home page
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", null);
        String savedPassword = sharedPreferences.getString("password", null);

        if (savedEmail != null && savedPassword != null) {
            EmailHolder.setUserEmail(savedEmail);

            Intent intent = new Intent(this, Home.class);
            intent.putExtra("email", savedEmail);
            intent.putExtra("password", savedPassword);

            startActivity(intent);
            finish();
        }




        TextView btn1 = findViewById(R.id.Singup);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Singup.class));
            }
        });


        Button button =findViewById(R.id.login_btn);

        EditText Email = findViewById(R.id.email);
        EditText Password = findViewById(R.id.password);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = Email.getText().toString();
                String userPassword = Password.getText().toString();


                UsersCollection.whereEqualTo("email",userEmail)
                        .whereEqualTo("password",userPassword)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    if (task.getResult() !=null && !task.getResult().isEmpty()){

                                        DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);


                                        String userEmail = userDoc.getString("email");


                                        EmailHolder.setUserEmail(userEmail);



                                        Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();


                                        Email.setText("");
                                        Password.setText("");

                                        saveUserDetails(userEmail, userPassword);
                                        Intent intent = new Intent(Login.this,Home.class);

                                        intent.putExtra("email", userDoc.getString("email"));
                                        intent.putExtra("password", userDoc.getString("password"));


                                        Log.d("SignInFragment", "Intent content: " + intent.getExtras());

                                        startActivity(intent);


                                    }else {
                                        Toast.makeText(Login.this,"Invalid Email or Password",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(Login.this,"Error"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT);
                                }
                            }
                        });





            }
        });






    }





}