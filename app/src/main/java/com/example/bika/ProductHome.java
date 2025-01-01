package com.example.bika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.bika.Adapter.CartListAdapter;
import com.example.bika.Adapter.ProductListAdapter;
import com.example.bika.Domain.Cart;
import com.example.bika.Domain.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductHome extends AppCompatActivity {
    private ProductListAdapter cartListAdapter; // Ensure correct scope for the adapters
    private FirebaseFirestore db;
    private FirebaseFirestore db2;
    private CollectionReference productsCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_home);



        ///////////////////////////////////////////
        Window window =ProductHome.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ProductHome.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_product_home);

/////////////////////////////////////////









        List<Product> cartList = new ArrayList<>(); // Initialize an empty list

        ListView listView = findViewById(R.id.listView);
        cartListAdapter = new ProductListAdapter(this, cartList);
        listView.setAdapter(cartListAdapter);

        db = FirebaseFirestore.getInstance();
        productsCollection = db.collection("Product");

        String Email = EmailHolder.getUserEmail();

        fetchProductsFromFirestore();


    }



    private void fetchProductsFromFirestore() {


        String Email = EmailHolder.getUserEmail();
        productsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            } else {

                List<Product> productList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String productName = documentSnapshot.getString("productName");
                        String productDesc = documentSnapshot.getString("productDesc");
                        String productPrice = documentSnapshot.getString("productPrice");
                        String imageURL = documentSnapshot.getString("imageURL");
                        String  productId= documentSnapshot.getString("productId");


                        Product product = new Product(imageURL, productDesc, productName, productPrice,productId);
                        productList.add(product);




                    }
                    cartListAdapter.setProductList(productList);



                }






                // Handle unsuccessful retrieval
            }
        });
    }









}