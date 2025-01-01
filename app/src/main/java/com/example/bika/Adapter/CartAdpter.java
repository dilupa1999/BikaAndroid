package com.example.bika.Adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bika.Domain.Cart;

import com.example.bika.R;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdpter extends RecyclerView.Adapter<CartAdpter.ProductViewHolder> {

    private List<Cart> productList;

    public void setProductList(List<Cart> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
      Cart cart = productList.get(position);
        holder.bind(cart);

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
        private  TextView productQtyTextView;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.cartimage);
            productNameTextView = itemView.findViewById(R.id.cartname);
            productDescTextView = itemView.findViewById(R.id.productdesc);
            productPriceTextView = itemView.findViewById(R.id.cartprice);
            productQtyTextView = itemView.findViewById(R.id.cartqty);


        }

        public void bind(Cart product) {
            String imageUrl = product.getImageURL();

            // Load image using Picasso into ImageView
            Picasso.get().load(imageUrl).into(productImageView);

            // Log the product name (for debugging)
            Log.d("ProductAdapter", "Product Name: " + product.getProductName());

            // Set product details to their respective
//            productRatingTextView .setText(product.getproductId());
            productNameTextView.setText(product.getProductName());
            productDescTextView.setText(product.getProductDesc());
            productPriceTextView.setText(product.getProductPrice());
            productQtyTextView.setText(product.getQty());
        }
    }
}