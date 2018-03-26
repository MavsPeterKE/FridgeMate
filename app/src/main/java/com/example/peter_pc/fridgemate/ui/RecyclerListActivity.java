package com.example.peter_pc.fridgemate.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.adapters.RecyclerViewAdapter;
import com.example.peter_pc.fridgemate.db.ProductModel;
import com.example.peter_pc.fridgemate.utils.Constants;
import com.example.peter_pc.fridgemate.utils.Methods;
import com.example.peter_pc.fridgemate.utils.NotificationUtils;
import com.example.peter_pc.fridgemate.utils.SimpleDecorator;
import com.example.peter_pc.fridgemate.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerListActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<ProductModel> product_models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);
        mRecyclerView = findViewById(R.id.productsList);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);


        productViewModel.getItems().observe(RecyclerListActivity.this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(@Nullable List<ProductModel> products) {
                if (!products.isEmpty()) {
                    product_models = products;
                    mAdapter = new RecyclerViewAdapter(products, getApplicationContext());
                    mRecyclerView.addItemDecoration(new SimpleDecorator(getApplicationContext()));
                    mRecyclerView.setAdapter(mAdapter);
                    processNotificationDate( product_models);
                }
            }

        });



    }

    public void processNotificationDate(List<ProductModel> products) {
        ArrayList<String> expiring_products = new ArrayList<>();
        int all_products_Expiring = 0, productsRemaining = 0;
        long daysLeftToExpire;

        for (ProductModel product : products) {
            daysLeftToExpire = new Methods().getRemainingDays(product.getExpiryDate());
            if (daysLeftToExpire >= 1 && daysLeftToExpire < 5) {
                expiring_products.add(product.getProductName() + " " + daysLeftToExpire + Constants.DAYS_LEFT_TO_EXPIRE);
                all_products_Expiring += 1;
                new NotificationUtils(this).inboxStyleNotification(expiring_products, all_products_Expiring, productsRemaining);
            }
            if (daysLeftToExpire > 0 && daysLeftToExpire > 5) {
            }
        }

    }
}
