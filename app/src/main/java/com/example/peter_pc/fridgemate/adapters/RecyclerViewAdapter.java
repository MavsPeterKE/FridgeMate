package com.example.peter_pc.fridgemate.adapters;

/**
 * Created by Peter on 12/7/17.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.models.ProductModel;
import com.example.peter_pc.fridgemate.utils.Methods;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<ProductModel> products;


    public RecyclerViewAdapter(List<ProductModel> products) {
        this.products=products;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recycer_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        ProductModel product =products.get(position);
        holder.tvProductName.setText(product.getProductName());
        holder.tvExpDate.setText(""+product.getExpiryDate());
       // holder.tvDayRem.setTextColor();
        int days= (int)new Methods().getRemainingDays(product.getExpiryDate());
        if (days<5) {

            if (days<1){
                holder.tvDayRem.setText("Expired");
                holder.tvDayRem.setTextColor(Color.BLACK);
            }else {
                holder.tvDayRem.setText("" +new Methods().getRemainingDays(product.getExpiryDate()) + " day (s) left to expire");
                holder.tvDayRem.setTextColor(Color.RED);
            }
        }else {
            holder.tvDayRem.setText("" + new Methods().getRemainingDays(product.getExpiryDate()) + " day (s) left to expire");
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /*public void addItems(List<BucketListModel> bucketListModelList) {
        this.bucketListModelList = bucketListModelList;
        notifyDataSetChanged();
    }*/

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName;
        private TextView tvExpDate;
        private TextView tvDayRem;

        RecyclerViewHolder(View view) {
            super(view);
            tvProductName = view.findViewById(R.id.prodName);
            tvExpDate = view.findViewById(R.id.dateTv);
            tvDayRem = view.findViewById(R.id.daysTv);
        }
    }
}