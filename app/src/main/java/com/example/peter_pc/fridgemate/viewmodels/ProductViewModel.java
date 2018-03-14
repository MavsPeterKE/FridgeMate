package com.example.peter_pc.fridgemate.viewmodels;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.models.ProductModel;
import com.example.peter_pc.fridgemate.db.ProductsDatabase;

import java.util.List;

/**
 * Created by Peter-PC on 3/5/2018.
 */

public class ProductViewModel extends AndroidViewModel {
    
    //creates database instance
    private ProductsDatabase appDatabase;

    Context context;

    //creates liveData instance
    private final LiveData<List<ProductModel>> productsList;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        appDatabase=ProductsDatabase.getDatabase(this.getApplication());
        productsList =appDatabase.productDao().getAllProducts() ;
    }

    //expose liveData to other classes
    public LiveData<List<ProductModel>> getItems() {

        return productsList;
    }

    //executes the asynch task
    public void saveProduct(final ProductModel productModel) {

        new saveAsyncTask(appDatabase).execute(productModel);
    }


    /*you cannot manipulate room from the main thread. This threat performs the database operation*/
    private static class saveAsyncTask extends AsyncTask<ProductModel, Void, Void> {

        private ProductsDatabase db;

        saveAsyncTask(ProductsDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ProductModel... params) {
            db.productDao().insertProduct(params[0]);

            Log.d("inserted",params[0].getProductName());
            return null;
        }


    }


}
