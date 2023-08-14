package com.rentall.model;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailsModel {
    String id, name, imageUrl, price, description, refund;

    public ProductDetailsModel() {
    }

    public ProductDetailsModel(String name, String price, String description, String refund, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.refund = refund;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefund() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedAmount = numberFormat.format(Integer.parseInt(refund));
        return formattedAmount;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedAmount = numberFormat.format(Integer.parseInt(price));
        return "₹" + formattedAmount + "/1 Day";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
