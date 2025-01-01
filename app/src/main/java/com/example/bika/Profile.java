package com.example.bika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ///////////////////////////////////////////
        Window window =Profile.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Profile.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_profile);

/////////////////////////////////////////


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference UsersCollection = db.collection("Users");

        String email  = EmailHolder.getUserEmail();


//user deatils

        UsersCollection.whereEqualTo("email",email)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult() !=null && !task.getResult().isEmpty()){

                                DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);

                                //setting email to static and passing to the email holder class
                                String userEmail = userDoc.getString("email");
                                String name = userDoc.getString("name");



                                TextView userNameTextView = findViewById(R.id.textView6);
                                userNameTextView.setText(userEmail);

                                TextView userEmailTextView = findViewById(R.id. textView5);
                                userEmailTextView.setText(name);




                            }else {

                            }
                        }else {

                        }
                    }
                });
//user


//deliver adress
     TextView delivery  = findViewById(R.id.textView11);

     delivery.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(Profile.this,DeliveryAdrass.class));
         }
     });

        //end delvieradress

          TextView Order    = findViewById(R.id.textView10);
          Order.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(Profile.this,MyOder.class));
              }
          });

        // back To Home

       Button btn = findViewById(R.id.button_back);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(Profile.this,Home.class));
           }
       });
//Back To Home

       //MyProfile
    TextView myprofile =  findViewById(R.id.textView7);

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,MyProfile.class));
            }
        });

        //MyProfile


        //login Redrect

     Button buttonlogin=   findViewById(R.id.logOut);
     buttonlogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             SharedPreferences sharedPreferences = Profile.this.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

             // Get the SharedPreferences editor to remove stored values
             SharedPreferences.Editor editor = sharedPreferences.edit();

             // Remove the stored email and password values
             editor.remove("email");
             editor.remove("password");

             // Apply changes to remove the values
             editor.apply();

             Toast.makeText(Profile.this,"Logged out",Toast.LENGTH_SHORT).show();


             Intent intent = new Intent(Profile.this, Login.class);
             startActivity(intent);
             Profile.this.finish();
         }
     });


    }
}