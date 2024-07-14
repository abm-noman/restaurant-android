package com.example.kutumbari;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChefOrderAdapter extends RecyclerView.Adapter<ChefOrderAdapter.ChefOrderViewHolder> {

    private List<Order> orderList;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderConfirm(Order order);
        void onOrderComplete(Order order);
    }

    public ChefOrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChefOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_chef, parent, false);
        return new ChefOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChefOrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tableTextView.setText("Table: " + order.getTable());
        holder.statusTextView.setText("Status: " + order.getStatus());

        StringBuilder items = new StringBuilder();
        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
            items.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        holder.itemsTextView.setText(items.toString());

        holder.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderConfirm(order);
            }
        });

        holder.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderComplete(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ChefOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tableTextView, statusTextView, itemsTextView;
        Button confirmButton, completeButton;

        public ChefOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tableTextView = itemView.findViewById(R.id.tableTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            confirmButton = itemView.findViewById(R.id.confirmButton);
            completeButton = itemView.findViewById(R.id.completeButton);
        }
    }
}