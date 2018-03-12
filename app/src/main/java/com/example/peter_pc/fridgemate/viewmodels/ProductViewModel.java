package com.example.peter_pc.fridgemate.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.peter_pc.fridgemate.db.ProductModel;
import com.example.peter_pc.fridgemate.db.ProductsDatabase;

import java.util.List;

/**
 * Created by Peter-PC on 3/5/2018.
 */

public class ProductViewModel extends AndroidViewModel {
    
    //creates database instance
    private ProductsDatabase appDatabase;

    //creates livedata instance
    private final LiveData<List<ProductModel>> productsList;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        appDatabase=ProductsDatabase.getDatabase(this.getApplication());
        productsList =appDatabase.productDao().getAllProducts() ;
    }

    //expose livedata to other classes
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
