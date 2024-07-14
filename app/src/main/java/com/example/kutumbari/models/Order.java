package com.example.kutumbari.models;

import java.util.Map;

public class Order {
    private int table;
    private Map<String, Integer> items;
    private String status;

    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public Order(int table, Map<String, Integer> items, String status) {
        this.table = table;
        this.items = items;
        this.status = status;
    }

    public int getTable() {
        return table;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }
}
