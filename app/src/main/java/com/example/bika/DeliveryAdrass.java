package com.example.bika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeliveryAdrass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_adrass);


///////////////////////////////////////////
        Window window = DeliveryAdrass.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(DeliveryAdrass.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_delivery_adrass);

/////////////////////////////////////////




        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String Email  = EmailHolder.getUserEmail();
        EditText home = findViewById(R.id.editTextText);
        EditText city = findViewById(R.id.editTextText2);


      Button btnAdress = findViewById(R.id.AdressSavebtn);


      btnAdress.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              CollectionReference UsersCollection = db.collection("Adress");
              String HomeAdress = home.getText().toString();
              String CityAdress = city.getText().toString();



              //validation
              if (HomeAdress.isEmpty()) {
                  Toast.makeText(DeliveryAdrass.this, "Please Enter Home adress", Toast.LENGTH_SHORT).show();
              } else if (CityAdress.isEmpty()) {
                  Toast.makeText(DeliveryAdrass.this, "Please Enter citye", Toast.LENGTH_SHORT).show();

              } else {


                  UsersCollection.whereEqualTo("email", Email)
                          .get()
                          .addOnCompleteListener(task -> {
                              if (task.isSuccessful()) {
                                  if (task.getResult() != null && !task.getResult().isEmpty()) {



                                      Toast.makeText(DeliveryAdrass.this, "Email already exist", Toast.LENGTH_SHORT).show();





                                  } else {




                                      Map<String, Object> Users = new HashMap<>();

                                      Users.put("email", Email);
                                      Users.put("homeadres", HomeAdress);
                                      Users.put("city", CityAdress);



                                      //pass data to home activity
                                      UsersCollection.add(Users)
                                              .addOnSuccessListener(documentReference -> {
//                                                    fname.setText("");
//                                                    lname.setText("");
//                                                    Email1.setText("");
//                                                    Password1.setText("");
                                                  Toast.makeText(DeliveryAdrass.this, "Successfull", Toast.LENGTH_SHORT).show();

                                              })
                                              .addOnFailureListener(e -> {
                                                  Toast.makeText(DeliveryAdrass.this, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
                                              });







                                  }
                              } else {
                                  Toast.makeText(DeliveryAdrass.this, "Error checking email" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });


              }

          }
      });






    }
}