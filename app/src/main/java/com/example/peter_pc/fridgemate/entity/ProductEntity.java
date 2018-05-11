package com.example.peter_pc.fridgemate.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Peter-PC on 3/6/2018.
 */

@Entity(tableName = "products_table")
public class ProductEntity {

    //Defines your table fields
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = " product_name")
    private String productName;

    @ColumnInfo(name = " product_barcode")
    private String productBcode;

    @ColumnInfo(name = " expiry_date")
    private String expiryDate;

    @ColumnInfo(name = " days_remaining")
    private String daysRemaining;

    //creates the class Constructor
    public ProductEntity(String productName, String productBcode, String expiryDate, String daysRemaining) {
        this.productName = productName;
        this.productBcode = productBcode;
        this.expiryDate = expiryDate;
        this.daysRemaining = daysRemaining;
    }

    //Getters and setters for accessing entity attribs
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBcode() {
        return productBcode;
    }

    public void setProductBcode(String productBcode) {
        this.productBcode = productBcode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(String daysRemaining) {
        this.daysRemaining = daysRemaining;
    }
}
