package com.example.peter_pc.fridgemate.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.adapters.RecyclerViewAdapter;
import com.example.peter_pc.fridgemate.db.ProductModel;
import com.example.peter_pc.fridgemate.utils.SimpleDecorator;
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
                    mRecyclerView.addItemDecoration(new SimpleDecorator(getApplicationContext()));
                    //listDecorator();
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            private void listDecorator() {
                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                        super.onDraw(c, parent, state);
                    }

                    @Override
                    public void onDraw(Canvas c, RecyclerView parent) {
                        super.onDraw(c, parent);
                    }

                    @Override
                    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                        super.onDrawOver(c, parent, state);
                    }

                    @Override
                    public void onDrawOver(Canvas c, RecyclerView parent) {
                        super.onDrawOver(c, parent);
                    }

                    @Override
                    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                        super.getItemOffsets(outRect, itemPosition, parent);
                    }

                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                    }
                });
            }
        });


    }
}
