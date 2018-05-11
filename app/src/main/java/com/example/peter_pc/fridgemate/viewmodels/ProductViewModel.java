package com.example.peter_pc.fridgemate.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.peter_pc.fridgemate.entity.ProductEntity;
import com.example.peter_pc.fridgemate.database.ProductsDatabase;

import java.util.List;

/**
 * Created by Peter-PC on 3/5/2018.
 */

public class ProductViewModel extends AndroidViewModel {

    //creates database instance
    private ProductsDatabase appDatabase;

    Context context;

    //creates liveData instance
    private final LiveData<List<ProductEntity>> productsList;
    //private final int allproducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        appDatabase = ProductsDatabase.getDatabase(this.getApplication());
        productsList = appDatabase.productDao().getAllProducts();
        //allproducts = appDatabase.productDao().getProductCount();
    }

    //expose liveData to other classes
    public LiveData<List<ProductEntity>> getItems() {

        return productsList;
    }

    //expose count result
    public int getProductCount() {

        return 20;
    }

    //executes the  save async task
    public void saveProduct(final ProductEntity productModel) {
        new insertProductAsync(appDatabase).execute(productModel);
    }

    /*you cannot manipulate room from the main thread. This thread performs the database operation*/
    private static class insertProductAsync extends AsyncTask<ProductEntity, Void, Void> {

        private ProductsDatabase db;

        insertProductAsync(ProductsDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ProductEntity... params) {
            db.productDao().insertProduct(params[0]);

            Log.d("inserted", params[0].getProductName());
            return null;
        }

    }

}
