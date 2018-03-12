package com.example.peter_pc.fridgemate.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Peter-PC on 3/6/2018.
 */

@Entity
public class ProductModel {

    //Defines your table fields
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String productName;
    private String productBcode;
    private String expiryDate;
    private String daysRemaining;

    //creates the class Constructor
    public ProductModel(String productName, String productBcode, String expiryDate, String daysRemaining) {
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
