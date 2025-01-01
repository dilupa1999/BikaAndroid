package com.example.bika.Domain;

// Product.java
public class Restulent {
    private String name;
    private int imageResource;

    public Restulent(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
