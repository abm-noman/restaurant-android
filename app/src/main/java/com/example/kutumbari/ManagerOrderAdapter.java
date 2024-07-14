package com.example.kutumbari;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Map;

public class ManagerOrderAdapter extends RecyclerView.Adapter<ManagerOrderAdapter.ManagerOrderViewHolder> {

    private List<Order> orderList;

    public ManagerOrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ManagerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_manager, parent, false);
        return new ManagerOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerOrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tableTextView.setText("Table: " + order.getTable());
        holder.statusTextView.setText("Status: " + order.getStatus());

        StringBuilder items = new StringBuilder();
        int totalAmount = 0;
        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
            items.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            totalAmount += entry.getValue() * getPrice(entry.getKey()); // Assuming getPrice() gives price for an item
        }
        holder.itemsTextView.setText(items.toString());
        holder.totalAmountTextView.setText("Total: $" + totalAmount);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ManagerOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tableTextView, statusTextView, itemsTextView, totalAmountTextView;

        public ManagerOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tableTextView = itemView.findViewById(R.id.tableTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            totalAmountTextView = itemView.findViewById(R.id.totalAmountTextView);
        }
    }

    private int getPrice(String itemName) {
        // Mock implementation, replace with actual price fetching logic
        switch (itemName) {
            case "Pizza":
                return 10;
            case "Burger":
                return 5;
            case "Pasta":
                return 8;
            default:
                return 0;
        }
    }
}
