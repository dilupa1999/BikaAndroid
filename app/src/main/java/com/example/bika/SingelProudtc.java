package com.example.bika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SingelProudtc extends AppCompatActivity {










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_proudtc);



        ///////////////////////////////////////////
        Window window =SingelProudtc.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(SingelProudtc.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_singel_proudtc);

/////////////////////////////////////////








        // Retrieve the product ID from the intent
        String productId = getIntent().getStringExtra("productId");

        Log.d("ok","ok"+productId);






        // singel product View



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference UsersCollection = db.collection("Product");

      CollectionReference cartCollection =  db.collection("Cart");

        String email  = EmailHolder.getUserEmail();


//user deatils

        UsersCollection.whereEqualTo("productId",productId)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult() !=null && !task.getResult().isEmpty()){

                                DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);

                                //setting email to static and passing to the email holder class
                                String image = userDoc.getString("imageURL");
                                String productdesc = userDoc.getString("productDesc");
                                String productname = userDoc.getString("productName");
                                String price = userDoc.getString("productPrice");

                                String priceString = userDoc.getString("productPrice");




                                TextView productName = findViewById(R.id.textView17);
                             productName.setText(productname);


                                TextView productDesc = findViewById(R.id.textView18);
                                productDesc.setText(productdesc);

                                TextView productPrice = findViewById(R.id.textView24);
                                productPrice.setText(price);

                                ImageView imageproduct =findViewById(R.id.imageView9);
                                imageproduct.setImageURI(Uri.parse(image));

                                ImageView imageProduct = findViewById(R.id.imageView9);





                                String imageUrl = "your_image_url_here"; // Replace this with your actual image URL from Firestore


                                Picasso.get().load(image).into(imageProduct);



                            }else {

                            }
                        }else {

                        }
                    }
                });
//user






        //singel product view







// Inside your activity or fragment
        TextView textViewQuantity = findViewById(R.id.textViewQuantity);
        Button buttonIncrement = findViewById(R.id.buttonIncrement);
        Button buttonDecrement = findViewById(R.id.buttonDecrement);

        final int[] quantity = {1}; // Initial quantity

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity[0]++;
                textViewQuantity.setText(String.valueOf(quantity[0]));
                // Add your logic to handle quantity increment

                TextView productPrice = findViewById(R.id.textView24);

                UsersCollection.whereEqualTo("productId",productId)

                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    if (task.getResult() !=null && !task.getResult().isEmpty()){

                                        DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);




                                        String priceString = userDoc.getString("productPrice");

                                        if (priceString != null && !priceString.isEmpty()) {
                                            try {
                                                int price = Integer.parseInt(priceString);

                                                // Calculate total price
                                                int totalPrice = price * quantity[0];

                                                // Update the TextView with the new total price
                                                productPrice.setText(String.valueOf(totalPrice));

                                            } catch (NumberFormatException e) {

                                                e.printStackTrace();
                                            }
                                        }



                                    }else {

                                    }
                                }else {

                                }
                            }
                        });


            }
        });

        buttonDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] > 1) {
                    quantity[0]--;
                    textViewQuantity.setText(String.valueOf(quantity[0]));
                    // Add your logic to handle quantity decrement




                    TextView productPrice = findViewById(R.id.textView24);

                    UsersCollection.whereEqualTo("productId",productId)

                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        if (task.getResult() !=null && !task.getResult().isEmpty()){

                                            DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);




                                            String priceString = userDoc.getString("productPrice");

                                            if (priceString != null && !priceString.isEmpty()) {
                                                try {
                                                    int price = Integer.parseInt(priceString);

                                                    // Calculate total price
                                                    int totalPrice = price * quantity[0];

                                                    // Update the TextView with the new total price
                                                    productPrice.setText(String.valueOf(totalPrice));

                                                } catch (NumberFormatException e) {

                                                    e.printStackTrace();
                                                }
                                            }



                                        }else {

                                        }
                                    }else {

                                    }
                                }
                            });











                }
            }
        });





  Button addcartbtn = findViewById(R.id.button5);

  addcartbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          UsersCollection.whereEqualTo("productId", productId)
                  .get()
                  .addOnCompleteListener(task -> {
                      if (task.isSuccessful()) {
                          if (task.getResult() != null && !task.getResult().isEmpty()) {
                              DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);

                              // Fetching data from TextViews
                              String productName = ((TextView) findViewById(R.id.textView17)).getText().toString();
                              String productDesc = ((TextView) findViewById(R.id.textView18)).getText().toString();
                              String productPrice = ((TextView) findViewById(R.id.textView24)).getText().toString();
                              String productQty = ((TextView) findViewById(R.id.textViewQuantity)).getText().toString();

                              String image = userDoc.getString("imageURL");

                              // Create a map to store the data
                              Map<String, Object> Cart = new HashMap<>();
                              Cart.put("productName", productName);
                              Cart.put("productDesc", productDesc);
                              Cart.put("productPrice", productPrice);
                              Cart.put("productQty", productQty);
                              Cart.put("imgURL", image);
                              Cart.put("UserEmail",email);

                              // Add data to the cartCollection
                              cartCollection.add(Cart)
                                      .addOnSuccessListener(documentReference -> {
                                          Toast.makeText(SingelProudtc.this, "Successfully added to cart", Toast.LENGTH_SHORT).show();
                                      })
                                      .addOnFailureListener(e -> {
                                          Toast.makeText(SingelProudtc.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                                      });

                          } else {
                              // Handle case where product is already added
                          }
                      } else {
                          // Handle error checking email
                          Toast.makeText(SingelProudtc.this, "Error checking email" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  });


      }
  });





    }
}