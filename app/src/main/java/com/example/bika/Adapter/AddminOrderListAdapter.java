package com.example.bika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bika.Domain.Oder;
import com.example.bika.R;

import java.util.List;

public class AddminOrderListAdapter extends BaseAdapter {
    private List<Oder> productList;
    private LayoutInflater inflater;

    public AddminOrderListAdapter(Context context, List<Oder> productList) {
        this.productList = productList;
        inflater = LayoutInflater.from(context);
    }

    public void setProductList(List<Oder> updatedProductList) {
        this.productList = updatedProductList;
        notifyDataSetChanged(); // Refresh the ListView
    }

    public List<Oder> getProductList() {
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
            convertView = inflater.inflate(R.layout.item_order, parent, false);
            holder = new ViewHolder();

            holder.productNameTextView = convertView.findViewById(R.id.orderid);
            holder.productDescTextView = convertView.findViewById(R.id.totel);
            holder.productPriceTextView = convertView.findViewById(R.id.adress );

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Oder product = productList.get(position);




        holder.productNameTextView.setText(product.getOrderId());
        holder.productDescTextView.setText(product.getTotal());
        holder.productPriceTextView.setText(product.getAdress());



        return convertView;
    }



    static class ViewHolder {

        TextView productNameTextView;
        TextView productDescTextView;
        TextView productPriceTextView;

    }
}
