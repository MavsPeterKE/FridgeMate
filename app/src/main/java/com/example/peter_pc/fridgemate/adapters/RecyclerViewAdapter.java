package com.example.peter_pc.fridgemate.adapters;

/**
 * Created by muho on 12/7/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.db.ProductModel;

import java.text.SimpleDateFormat;
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
        holder.tvExpDate.setText(product.getExpiryDate());
        holder.tvDayRem.setText(product.getDaysRemaining());

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
        }
    }
}