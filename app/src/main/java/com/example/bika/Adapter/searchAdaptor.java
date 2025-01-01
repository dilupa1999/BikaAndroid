package com.example.bika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bika.Domain.Product;
import com.example.bika.R;
import com.example.bika.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class searchAdaptor extends RecyclerView.Adapter<searchAdaptor.PViewHolder> {

    private Context context;
    private ArrayList<Product> arrayList;

    public searchAdaptor(Context context, ArrayList<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public searchAdaptor.PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search, parent, false);
        return new PViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull searchAdaptor.PViewHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.textView.setText(product.getProductName());
        holder.textView1.setText(product.getProductPrice());

        String imageUrl = product.getImageURL();
        Picasso.get().load(imageUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();

    }
    public static class PViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        ImageView imageView;

        public PViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewS);
            textView = itemView.findViewById(R.id.Pharmacytxt);
            textView1 = itemView.findViewById(R.id.PharmacyLocationtxt);

        }
    }
}
