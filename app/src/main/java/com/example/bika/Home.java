package com.example.bika;




import androidx.annotation.NonNull;


import com.example.bika.Adapter.ProductAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;









import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import com.example.bika.Adapter.ProductAdapter2;
import com.example.bika.Domain.PopulerItems;
import com.example.bika.Domain.Product;
import com.example.bika.Domain.Restulent;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private LinearLayout verticalLayout;


    private RecyclerView recyclerView;

    private ProductAdapter adapter;
    private ProductAdapter2 adapter2;
    private FirebaseFirestore db;
    private FirebaseFirestore db1;
    private CollectionReference productsCollection;
    private CollectionReference productsCollection1;

    private List<Product> productList;

    private String city; // Declare private variables
    private String homeAddress;
    private String combinedAddress;



    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

///////////////////////////////////////////
        Window window = Home.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Home.this, androidx.cardview.R.color.cardview_dark_background));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home);

/////////////////////////////////////////






        fetchUserAddressFromFirestore();


        // bottom navigation

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent = null;

                        if (item.getItemId() == R.id.menu_order) {
                            intent = new Intent(Home.this, Login.class);
                        } else if (item.getItemId() == R.id.menu_restaurant) {
                            intent = new Intent(Home.this, Profile.class);
                        } else if (item.getItemId() == R.id.menu_home) {
                            intent = new Intent(Home.this, ProductCart.class);
                        } else if (item.getItemId() == R.id.menu_favorite) {
                            intent = new Intent(Home.this, ProductHome.class);
                        } else if (item.getItemId() == R.id.menu_profile) {
                            intent = new Intent(Home.this, AdminActivity.class);
                        }

                        if (intent != null) {
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                            return true;
                        }

                        return false;
                    }
                });
        // bottom navigation



        //singel product viewgoin code

       RecyclerView r1= findViewById(R.id.recyclerView1);






//fastDelivery food

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        productsCollection = db.collection("Product");


        fetchProductsFromFirestore();



        //fastdelivey end

//populerItem

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        adapter2  = new ProductAdapter2();
        recyclerView.setAdapter(adapter2);

        db1 = FirebaseFirestore.getInstance();
        productsCollection1 = db1.collection("Product");


        fetchProductsrealetetedFromFirestore();

//populerItemend


//searc

      ImageView search =  findViewById(R.id.imageView2);
      search.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(Home.this, SearchActivity.class));
          }
      });

        //serach


    }
//fast delivery
    private void fetchProductsFromFirestore() {
        productsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Product> productList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String productName = documentSnapshot.getString("productName");
                        String productDesc = documentSnapshot.getString("productDesc");
                        String productPrice = documentSnapshot.getString("productPrice");
                        String imageURL = documentSnapshot.getString("imageURL");
                        String Proudcutid = documentSnapshot.getString("productId");

                        Product product = new Product(productName, productDesc, productPrice, imageURL,Proudcutid);
                        productList.add(product);
                    }
                    adapter.setProductList(productList);
                }
            } else {
                // Handle failure or error fetching data from Firestore
            }
        });
    }


    //    fastdeliveryend









    //realeteted food

    private void fetchProductsrealetetedFromFirestore() {
        productsCollection1.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<PopulerItems> productList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        String productName = documentSnapshot.getString("productName");
                        String productDesc = documentSnapshot.getString("productDesc");
                        String productPrice = documentSnapshot.getString("productPrice");
                        String imageURL = documentSnapshot.getString("imageURL");

                        PopulerItems product = new PopulerItems(productName, productDesc, productPrice, imageURL);
                        productList.add(product);
                    }
                    adapter2.setProductList(productList);
                }
            } else {
                // Handle failure or error fetching data from Firestore
            }
        });
    }
    ////////realeteted end

    private View createProductCard(Restulent restulent) {
        View view = getLayoutInflater().inflate(R.layout.restulent_card, verticalLayout, false);

        ImageView restulentproductImage = view.findViewById(R.id.restulentproductImage);
        TextView restulentproductName = view.findViewById(R.id.restulentproductName);

        restulentproductImage.setImageResource(restulent.getImageResource());
        restulentproductName.setText(restulent.getName());

        // Set onClickListener or other properties for the card if needed

        return view;

    }


    // bottom navigation


    // bottom navigation

    //adress

    private void fetchUserAddressFromFirestore() {
        db = FirebaseFirestore.getInstance();

        CollectionReference deliverAdress = db.collection("Adress");
        String Email = EmailHolder.getUserEmail();

        deliverAdress.whereEqualTo("email", Email)
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


                       TextView adressLine =   findViewById(R.id.textView9);
                       adressLine.setText(combinedAddress);



                        } else {

                        }
                    } else {

                    }
                });
    }




    //adress


}
