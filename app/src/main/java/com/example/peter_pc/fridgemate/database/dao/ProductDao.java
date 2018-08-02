package com.example.peter_pc.fridgemate.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.peter_pc.fridgemate.database.entity.ProductEntity;

import java.util.List;

/**
 * Created by Peter-PC on 3/6/2018.
 */

@Dao
public interface ProductDao {

    @Query("select * from products_table group by ` product_barcode`" +
            "")
    LiveData<List<ProductEntity>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProduct(ProductEntity product);

    @Query("SELECT COUNT(*) from products_table")
    int countProducts();

    @Query("SELECT COUNT( 'product_barcode') from products_table  WHERE ` product_barcode`=:barcode")
    int getProductCount(String barcode);


    @Delete
    int delete(ProductEntity productModel);

}

