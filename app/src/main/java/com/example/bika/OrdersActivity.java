package com.example.bika;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bika.Adapter.CartListAdapter;
import com.example.bika.Adapter.OrderListAdapter;
import com.example.bika.Domain.Cart;
import com.example.bika.Domain.Oder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private OrderListAdapter cartListAdapter; // Ensure correct scope for the adapters
    private FirebaseFirestore db;
    private CollectionReference productsCollection;

    private String city; // Declare private variables
    private String homeAddress;
    private String combinedAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_oder);



        ///////////////////////////////////////////
        Window window = OrdersActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(OrdersActivity.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_my_oder);

/////////////////////////////////////////






        List<Oder> cartList = new ArrayList<>(); // Initialize an empty list

        ListView listView = findViewById(R.id.listView);
        cartListAdapter = new OrderListAdapter(this, cartList);
        listView.setAdapter(cartListAdapter);

        db = FirebaseFirestore.getInstance();
        productsCollection = db.collection("Order");
        CollectionReference deliverAdress = db.collection("Adress");

        String Email = EmailHolder.getUserEmail();



        fetchUserAddressFromFirestore();



    }



    private void fetchUserAddressFromFirestore() {

        CollectionReference deliverAdress = db.collection("Adress");
        String Email = EmailHolder.getUserEmail();

        deliverAdress
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            DocumentSnapshot userDoc = task.getResult().getDocuments().get(0);


                            String retrievedCity = userDoc.getString("city");
                            String retrievedHomeAddress = userDoc.getString("homeadres");


                            if (retrievedCity != null) {
                                city = retrievedCity;
                            }
                            if (retrievedHomeAddress != null) {
                                homeAddress = retrievedHomeAddress;
                            }


                            combinedAddress = city + ", " + homeAddress;
                            Log.d("Total", "Total Quantity: " + combinedAddress);


                            fetchProductsFromFirestore();



                        } else {

                        }
                    } else {

                    }
                });
    }








    private void fetchProductsFromFirestore() {
        // Assuming productsCollection refers to your Firestore CollectionReference


        productsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Oder> productList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String productName = documentSnapshot.getString("orderId");

                        String productPrice = documentSnapshot.getString("total");



                        Oder product = new Oder(productName, productPrice,combinedAddress );
                        productList.add(product);
                    }
                    cartListAdapter.setProductList(productList);

                }
            } else {
                // Handle unsuccessful retrieval
            }
        });
    }


}