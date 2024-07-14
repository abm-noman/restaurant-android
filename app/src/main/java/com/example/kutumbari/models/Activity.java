package com.example.kutumbari.models;

public class Activity {
    private String userId;
    private String action;
    private long timestamp;

    public Activity() {
        // Default constructor required for calls to DataSnapshot.getValue(Activity.class)
    }

    public Activity(String userId, String action, long timestamp) {
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
