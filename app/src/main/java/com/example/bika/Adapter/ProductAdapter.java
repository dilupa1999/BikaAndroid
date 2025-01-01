package com.example.bika.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bika.Domain.Product;
import com.example.bika.R;

import com.example.bika.SingelProudtc;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);

        // Get the product at the clicked position


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the product ID
                String productId = product.getproductId();

                // Start the SingleProductView activity and pass the product ID via intent
                Intent intent = new Intent(v.getContext(), SingelProudtc.class);
                intent.putExtra("productId", productId);
                v.getContext().startActivity(intent);
            }
        });






    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImageView;
        private TextView productNameTextView;
        private TextView productDescTextView;
        private TextView productPriceTextView;

        private  TextView   productRatingTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImage);
            productNameTextView = itemView.findViewById(R.id.productName);
            productDescTextView = itemView.findViewById(R.id.producttitel);
            productPriceTextView = itemView.findViewById(R.id.deliveryCost);
//             productRatingTextView       =itemView.findViewById(R.id.rating);
        }

        public void bind(Product product) {
            String imageUrl = product.getProductPrice();

            // Load image using Picasso into ImageView
            Picasso.get().load(imageUrl).into(productImageView);

            // Log the product name (for debugging)
            Log.d("ProductAdapter", "Product Name: " + product.getProductName());

            // Set product details to their respective
//            productRatingTextView .setText(product.getproductId());
            productNameTextView.setText("LKR ."+product.getProductName()+" .00");
            productDescTextView.setText(product.getProductDesc());
            productPriceTextView.setText(product.getImageURL());
        }
    }
}