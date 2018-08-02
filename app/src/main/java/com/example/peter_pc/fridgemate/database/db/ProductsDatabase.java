package com.example.peter_pc.fridgemate.database.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.peter_pc.fridgemate.database.dao.ProductDao;
import com.example.peter_pc.fridgemate.database.entity.ProductEntity;

/**
 * Created by Peter-PC on 3/6/2018.
 */
@Database(entities = {ProductEntity.class},version = 9)
public abstract class ProductsDatabase extends RoomDatabase {
    private static  ProductsDatabase INSTANCE;

    //ensure only single context of the class at ago
    public static  ProductsDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),  ProductsDatabase.class, "products_db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    //Enables access to the DAO
    public abstract ProductDao productDao();

}
