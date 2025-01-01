package com.example.bika.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bika.Domain.Cart;
import com.example.bika.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends BaseAdapter {
    private List<Cart> productList;
    private LayoutInflater inflater;

    public CartListAdapter(Context context, List<Cart> productList) {
        this.productList = productList;
        inflater = LayoutInflater.from(context);
    }

    public void setProductList(List<Cart> updatedProductList) {
        this.productList = updatedProductList;
        notifyDataSetChanged(); // Refresh the ListView
    }

    public List<Cart> getProductList() {

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
            holder.productNameTextView = convertView.findViewById(R.id.cartprice);
            holder.productDescTextView = convertView.findViewById(R.id.productdesc);
            holder.productPriceTextView = convertView.findViewById(R.id.cartqty );
            holder.productQtyTextView = convertView.findViewById(R.id. cartqty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cart product = productList.get(position);


        Picasso.get().load(product.getImageURL()).into(holder.productImageView);



        holder.productNameTextView.setText(product.getProductName());
        holder.productDescTextView.setText(product.getProductDesc());
        holder.productPriceTextView.setText(product.getProductPrice());
        holder.productQtyTextView.setText(product.getQty());


        return convertView;
    }



    static class ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productDescTextView;
        TextView productPriceTextView;
        TextView productQtyTextView;
    }
}
