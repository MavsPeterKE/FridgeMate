package com.example.peter_pc.fridgemate.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.peter_pc.fridgemate.database.db.ProductsDatabase;
import com.example.peter_pc.fridgemate.database.entity.ProductEntity;

import java.util.List;

/**
 * Created by Peter-PC on 3/5/2018.
 */

public class ProductViewModel extends AndroidViewModel {

    //creates liveData instance
    private final LiveData<List<ProductEntity>> productsList;
    Context context;
    //creates database instance
    private ProductsDatabase appDatabase;

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
        long row;
        private ProductsDatabase db;

        insertProductAsync(ProductsDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ProductEntity... params) {
            row = db.productDao().insertProduct(params[0]);
            int count = db.productDao().countProducts();
            Log.e("inserted__", "" + row);
            Log.e("inCount__", "" + count);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            longValue(row);

        }
        //new ProductViewModel.longVa

        public long longValue(long row) {
            return row;
        }
    }

}
