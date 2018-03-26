package com.example.peter_pc.fridgemate.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
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

    Context context;

    //creates liveData instance
    private final LiveData<List<ProductModel>> productsList;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        appDatabase = ProductsDatabase.getDatabase(this.getApplication());
        productsList = appDatabase.productDao().getAllProducts();
    }

    //expose liveData to other classes
    public LiveData<List<ProductModel>> getItems() {

        return productsList;
    }

    //executes the  save async task
    public void saveProduct(final ProductModel productModel) {

        new SaveAsyncTask(appDatabase).execute(productModel);
        //DbOperation.executeBackground(context,productModel);
    }

    /*you cannot manipulate room from the main thread. This thread performs the database operation*/
    private static class SaveAsyncTask extends AsyncTask<ProductModel, Void, Void> {

        private ProductsDatabase db;

        SaveAsyncTask(ProductsDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ProductModel... params) {
            db.productDao().insertProduct(params[0]);

            Log.d("inserted", params[0].getProductName());
            return null;
        }

    }

    //executes the  save async task
    public void getProductsCount() {
        new productsCountAsyncTask(appDatabase);
    }


    /*you cannot manipulate room from the main thread. This thread performs the database operation*/
    private static class productsCountAsyncTask extends AsyncTask<ProductModel, Void, Void> {

        private ProductsDatabase db;

        productsCountAsyncTask(ProductsDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(ProductModel... productModels) {
            db.productDao().getProductCount();
            Log.e("doInBackground: ", "Product Count" + db.productDao().getProductCount());
            return null;
        }
    }
}
