package com.example.peter_pc.fridgemate.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.adapters.ProductsRecyclerAdapter;
import com.example.peter_pc.fridgemate.database.entity.ProductEntity;
import com.example.peter_pc.fridgemate.notification.NotificationHelper;
import com.example.peter_pc.fridgemate.utils.Constants;
import com.example.peter_pc.fridgemate.utils.Methods;
import com.example.peter_pc.fridgemate.utils.NotificationUtils;
import com.example.peter_pc.fridgemate.utils.RecyclerTouchListener;
import com.example.peter_pc.fridgemate.utils.SimpleDecorator;
import com.example.peter_pc.fridgemate.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RecyclerListActivity extends AppCompatActivity {
    Context mContext;
    List<ProductEntity> product_models;
    private ProductViewModel productViewModel;
    private RecyclerView mRecyclerView;
    private ProductsRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);
        mRecyclerView = findViewById(R.id.productsList);
        mRecyclerView.setHasFixedSize(true);
        mContext = this;

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);


        productViewModel.getItems().observe(RecyclerListActivity.this, new
                Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> products) {
                if (!products.isEmpty()) {
                    product_models = products;
                    mAdapter = new ProductsRecyclerAdapter(products, getApplicationContext());
                    mRecyclerView.addItemDecoration(new SimpleDecorator(getApplicationContext()));
                    mRecyclerView.setAdapter(mAdapter);
                    processNotificationDate(product_models);
                }
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, final int position) {
                new SweetAlertDialog(RecyclerListActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Product will be removed Completely")
                        .setConfirmText("Yes,delete it!")
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).setConfirmButton("Okay", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        productViewModel.deleteProduct(product_models.get(position));
                        Toast.makeText(mContext, "Product Deleted", Toast.LENGTH_SHORT).show();
                        sweetAlertDialog.dismiss();
                    }
                }).show();
            }
        }));


    }

    public void processNotificationDate(List<ProductEntity> products) {
        ArrayList<String> expiring_products = new ArrayList<>();
        int all_products_Expiring = 0, productsRemaining = 0;
        long daysLeftToExpire;

        for (ProductEntity product : products) {
            daysLeftToExpire = new Methods().getRemainingDays(product.getExpiryDate());
            if (daysLeftToExpire >= 1 && daysLeftToExpire < 5) {
                expiring_products.add(product.getProductName() + " " + daysLeftToExpire +
                        Constants.DAYS_LEFT_TO_EXPIRE);
                all_products_Expiring += 1;
                new NotificationUtils(this).inboxStyleNotification(expiring_products,
                        all_products_Expiring, productsRemaining);
                //setScheduleNotification();
            }
            if (daysLeftToExpire > 0 && daysLeftToExpire > 5) {
            }
        }

    }

    public void setScheduleNotification() {
        boolean isEnabled = true;

        if (isEnabled) {
            NotificationHelper.scheduleRepeatingRTCNotification(mContext, "00", "01");
            NotificationHelper.enableBootReceiver(mContext);
        } else {
            NotificationHelper.cancelAlarmRTC();
            NotificationHelper.disableBootReceiver(mContext);
        }
    }
}
