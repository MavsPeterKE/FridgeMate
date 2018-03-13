package com.example.peter_pc.fridgemate.adapters;

/**
 * Created by Peter on 12/7/17.
 */

import android.graphics.Color;
import android.renderscript.Sampler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.db.ProductModel;

import java.text.SimpleDateFormat;
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
        int days= (int)getRemainingDays(product.getExpiryDate());
        if (days<5) {
            holder.tvDayRem.setText("" + getRemainingDays(product.getExpiryDate()) + " day (s) left to expire");
            holder.tvDayRem.setTextColor(Color.RED);
        }else {
            holder.tvDayRem.setText("" + getRemainingDays(product.getExpiryDate()) + " day (s) left to expire");
        }

    }

    private long getRemainingDays(String expiryTime){
        long expirydate= Long.valueOf(expiryTime);
        Date todayDate= Calendar.getInstance().getTime();
        long diff=expirydate-todayDate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = (hours / 24) + 1;
        Log.e( "onDateSet: ",""+expirydate );
        Log.e( "onDateSet: ",""+todayDate.getTime() );
        return days;


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