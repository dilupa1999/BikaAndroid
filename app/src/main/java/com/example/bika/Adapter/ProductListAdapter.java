package com.example.bika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bika.Domain.Product;
import com.example.bika.R;
import com.example.bika.SingelProudtc;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    private List<Product> productList;
    private LayoutInflater inflater;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        inflater = LayoutInflater.from(context);
    }

    public void setProductList(List<Product> updatedProductList) {
        this.productList = updatedProductList;
        notifyDataSetChanged(); // Refresh the ListView
    }

    public List<Product> getProductList() {
        return productList;
    }
    @Override
    public int getCount() {
        return productList != null ? productList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cart, parent, false);
            holder = new ViewHolder();
            holder.productImageView = convertView.findViewById(R.id.cartimage);
            holder.productNameTextView = convertView.findViewById(R.id.cartname);
            holder.productDescTextView = convertView.findViewById(R.id.productdesc);
            holder.productPriceTextView = convertView.findViewById(R.id.cartprice );

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        Product product = productList.get(position);

        final Product product = productList.get(position);
        // Load image using Picasso into ImageView
        Picasso.get().load(product.getImageURL()).into(holder.productImageView);



        holder.productNameTextView.setText(product.getProductName());
        holder.productDescTextView.setText(product.getProductDesc());
        holder.productPriceTextView.setText(product.getProductPrice());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click, navigate to single product view passing product details
                // For example:



                 Intent intent = new Intent(v.getContext(), SingelProudtc.class);
                 intent.putExtra("productId", product.getproductId());
                 v.getContext().startActivity(intent);

            }
        });



        return convertView;
    }



    static class ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productDescTextView;
        TextView productPriceTextView;

    }
}
