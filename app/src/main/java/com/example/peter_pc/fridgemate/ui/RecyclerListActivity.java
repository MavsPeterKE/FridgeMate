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
import com.example.peter_pc.fridgemate.viewmodels.ProductViewModel;

import java.util.List;

public class RecyclerListActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<ProductModel> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.productsList);

        //query data from the db
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        productViewModel= ViewModelProviders.of(this).get(ProductViewModel.class);


        productViewModel.getItems().observe(RecyclerListActivity.this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(@Nullable List<ProductModel> products) {
                if (!products.isEmpty()) {
                    products=products;
                    mAdapter = new RecyclerViewAdapter(products);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });


    }
}
