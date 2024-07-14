package com.example.kutumbari.models;

public class MenuItem {
    private String name;
    private double price;
    private String description;

    public MenuItem() {
        // Default constructor required for calls to DataSnapshot.getValue(MenuItem.class)
    }

    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
