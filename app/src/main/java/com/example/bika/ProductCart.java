package com.example.bika;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bika.Adapter.CartAdpter;
import com.example.bika.Adapter.CartListAdapter;
import com.example.bika.Adapter.ProductAdapter;
import com.example.bika.Adapter.ProductAdapter2;
import com.example.bika.Domain.Cart;
import com.example.bika.Domain.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ProductCart extends AppCompatActivity {
///notificcation
    private NotificationManager notificationManager;
    private String chanelId = "info";
    //
    private CartListAdapter cartListAdapter; // Ensure correct scope for the adapters
    private FirebaseFirestore db;
    private FirebaseFirestore db2;
    private CollectionReference productsCollection;

    private TextView subtotal;
    private  TextView totelview;
    private Double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);


        ///////////////////////////////////////////
        Window window = ProductCart.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ProductCart.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_product_cart);

/////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(chanelId, "INFO", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            channel.setDescription("this is sign in information ");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setVibrationPattern(new long[]{0, 400, 200, 400});
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }







//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference cartCollection =  db.collection("Cart");

        // Assuming you have declared 'cartList' somewhere before this point
        List<Cart> cartList = new ArrayList<>(); // Initialize an empty list

        ListView listView = findViewById(R.id.listView);
        cartListAdapter = new CartListAdapter(this, cartList);
        listView.setAdapter(cartListAdapter);

        db = FirebaseFirestore.getInstance();
        productsCollection = db.collection("Cart");

        String Email = EmailHolder.getUserEmail();

        fetchProductsFromFirestore();





        //checout
//       Button checkbtn = findViewById(R.id.checkoutButton);
//        checkbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                db2 = FirebaseFirestore.getInstance();
//                CollectionReference usersOrder = db2.collection("Order");
//
//                subtotal = findViewById(R.id.subtotal);
//                String subtotalText = subtotal.getText().toString(); // Get the text content
//
//
//
//                TextView totalView = findViewById(R.id.totalPrice);
//                String totalText = totalView.getText().toString(); // Get the text content
//
//
//
//
//                String Email = EmailHolder.getUserEmail();
//                String phoneNumber = EmailHolder.getuserMobile();
//
//
//                Log.d("Total", "Total Quantity: " + Email);
//                Log.d("Total", "Total Quantity: " + phoneNumber);
//
//
//                usersOrder.whereEqualTo("email", Email)
//                        .get()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                if (task.getResult() != null && !task.getResult().isEmpty()) {
//
//
//
//
//
//                                    Toast.makeText(ProductCart.this, "", Toast.LENGTH_SHORT).show();
//
//
//
//                                } else {
//
//
//
//                                    Map<String, Object> userOrderData = new HashMap<>();
//
//                                    userOrderData.put("email", Email);
//                                    userOrderData.put("subtotal", subtotalText); // Store as String
//                                    userOrderData.put("total", totalText); // Store as String
//                                    userOrderData.put("phoneNumber", phoneNumber);
//
//                                    usersOrder.add(userOrderData)
//                                            .addOnSuccessListener(documentReference -> {
//                                                Toast.makeText(ProductCart.this, "Successfully added order", Toast.LENGTH_SHORT).show();
//                                            })
//                                            .addOnFailureListener(e -> {
//                                                Toast.makeText(ProductCart.this, "Failed to add order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            });
//
//
//
//
//
//
//
//                                }
//                            } else {
//                                Toast.makeText(ProductCart.this, "Error checking email: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
//
//
//
//            }
//        });



        Button checkbtn = findViewById(R.id.checkoutButton);
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db2 = FirebaseFirestore.getInstance();
                CollectionReference usersOrder = db2.collection("Order");
                CollectionReference userCart = db2.collection("Cart"); // Reference to the "cart" collection

                subtotal = findViewById(R.id.subtotal);
                String subtotalText = subtotal.getText().toString(); // Get the text content

                TextView totalView = findViewById(R.id.totalPrice);
                String totalText = totalView.getText().toString(); // Get the text content

                String Email = EmailHolder.getUserEmail();
                String phoneNumber = EmailHolder.getuserMobile();

                Log.d("Total", "Total Quantity: " + Email);
                Log.d("Total", "Total Quantity: " + phoneNumber);

                usersOrder
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null && !task.getResult().isEmpty()) {
                                    Map<String, Object> userOrderData = new HashMap<>();

                                    userOrderData.put("email", Email);
                                    userOrderData.put("subtotal", subtotalText); // Store as String
                                    userOrderData.put("total", totalText); // Store as String
                                    String uniqueID = UUID.randomUUID().toString();
                                    userOrderData.put("orderId", uniqueID);


                                    usersOrder.add(userOrderData)
                                            .addOnSuccessListener(documentReference -> {
                                                // On successfully adding order data
                                                Toast.makeText(ProductCart.this, "Successfully added order", Toast.LENGTH_SHORT).show();

                                                // Now, delete data from the "cart" collection
                                                userCart.whereEqualTo("UserEmail", Email)
                                                        .get()
                                                        .addOnCompleteListener(cartTask -> {
                                                            if (cartTask.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : cartTask.getResult()) {

                                                                    document.getReference().delete()
                                                                            .addOnSuccessListener(aVoid -> {
                                                                                // Deletion successful
                                                                                Log.d("Delete", "DocumentSnapshot successfully deleted from cart!");

                                                                                Intent intent = new Intent(ProductCart.this,Home.class);


                                                                                startActivity(intent);



                                                                                //notifacton

                                                                                PendingIntent pendingIntent = PendingIntent
                                                                                        .getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(),MyOder.class), PendingIntent.FLAG_IMMUTABLE);
//                pending intent

                                                                                Notification notification = new NotificationCompat.Builder(getApplicationContext(), chanelId)
                                                                                        .setAutoCancel(true)
                                                                                        .setSmallIcon(R.drawable.cart_24)
                                                                                        .setContentTitle("Ordering Confirm!")
                                                                                        .setContentText("Thank you for your Order. Click here to view you orders.")
                                                                                        .setColor(Color.BLUE)
                                                                                        .setContentIntent(pendingIntent)
                                                                                        .build();

                                                                                //            Show the notification
                                                                                notificationManager.notify(1, notification);


                                                                                ///




                                                                            })
                                                                            .addOnFailureListener(e -> {
                                                                                // Deletion failed
                                                                                Log.w("Delete", "Error deleting document from cart", e);
                                                                            });


                                                                }

                                                                fetchProductsFromFirestore( );
                                                            } else {
                                                                Log.d("Delete", "Error getting cart documents: ", cartTask.getException());
                                                            }
                                                        });

                                            })
                                            .addOnFailureListener(e -> {
                                                // Failed to add order data
                                                Toast.makeText(ProductCart.this, "Failed to add order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });











                                } else {





                                    Toast.makeText(ProductCart.this, "", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ProductCart.this, "Error checking email: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });




        //cheout






    }




    private void fetchProductsFromFirestore() {
        // Assuming productsCollection refers to your Firestore CollectionReference

        String Email = EmailHolder.getUserEmail();
        productsCollection.whereEqualTo("UserEmail", Email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Cart> productList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String productName = documentSnapshot.getString("productName");
                        String productDesc = documentSnapshot.getString("productDesc");
                        String productPrice = documentSnapshot.getString("productPrice");
                        String imageURL = documentSnapshot.getString("imageURL");
                        String productQty = documentSnapshot.getString("productQty");

                        Cart product = new Cart(imageURL, productDesc, productName, productPrice, productQty);
                        productList.add(product);
                    }
                    cartListAdapter.setProductList(productList);
                    calculateTotal();
                }
            } else {
                // Handle unsuccessful retrieval
            }
        });
    }







    private void calculateTotal() {
        int totalQuantity = 0;
        double totalPrice = 0.0;

        List<Cart> productList = cartListAdapter.getProductList(); // Get the current list

        if (productList != null) {
            for (Cart product : productList) {
                String quantityStr = product.getQty();
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    totalQuantity += quantity;

                    String priceStr = product.getProductPrice();
                    if (priceStr != null && !priceStr.trim().isEmpty()) {
                        double price = Double.parseDouble(priceStr.trim());
                        totalPrice += quantity * price;
                    } else {
                        Log.e("Total", "Price string is null or empty for product: " + product.getProductPrice());
                        // You might handle this scenario by setting a default price or skipping this product
                    }
                } catch (NumberFormatException e) {
                    Log.e("Total", "Error parsing quantity or price: " + e.getMessage());
                    Log.e("Total", "Product: " + product.getProductPrice() + ", Quantity: " + quantityStr + ", Price: " + product.getProductPrice());
                    // Handle the situation where the string is not a valid number
                    // For instance, skipping this product or providing a default value
                }
            }

            Log.d("Total", "Total Quantity: " + totalQuantity);
            Log.d("Total", "Total Price: " + totalPrice);
                    TextView subtotal  = findViewById(R.id.subtotal);
                    subtotal.setText(Double.toString(totalPrice));
                Double total   = totalPrice+200;
            TextView totelview  = findViewById(R.id.totalPrice);
                    totelview.setText(Double.toString(total));

        }
    }

}






