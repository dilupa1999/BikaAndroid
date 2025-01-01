package com.example.bika.Domain;

// Product.java
public class PopulerItems {
    private String imageURL ;

    private  String productDesc;

    private String productName;


    private String productPrice;


    public PopulerItems(String imageURL, String productDesc, String productName, String productPrice) {
        this.imageURL = imageURL;
        this.productDesc = productDesc;
        this.productName = productName;
        this.productPrice = productPrice;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public PopulerItems() {
    }
}
