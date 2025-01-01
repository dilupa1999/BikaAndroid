package com.example.bika.Adapter;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bika.Domain.PopulerItems;
import com.example.bika.Domain.Product;
import com.example.bika.R;

import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter2 extends RecyclerView.Adapter<ProductAdapter2.ProductViewHolder> {

    private List<PopulerItems> productList;

    public void setProductList(List<PopulerItems> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card2, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PopulerItems product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImageView1;
        private TextView productNameTextView1;
        private TextView productDescTextView1;
        private TextView productPriceTextView1;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView1 = itemView.findViewById(R.id.productImage1);
            productNameTextView1 = itemView.findViewById(R.id.productName1);
            productDescTextView1 = itemView.findViewById(R.id.producttitel1);
            productPriceTextView1 = itemView.findViewById(R.id.deliveryCost1);
        }

        public void bind(PopulerItems product) {
            String imageUrl = product.getProductPrice();

            // Load image using Picasso into ImageView
            Picasso.get().load(imageUrl).into(productImageView1);

            // Log the product name (for debugging)
            Log.d("ProductAdapter", "Product Name: " + product.getProductName());

            // Set product details to their respective TextViews
            productNameTextView1.setText(product.getProductName());
            productDescTextView1.setText(product.getProductDesc());
            productPriceTextView1.setText(product.getImageURL());
        }
    }
}