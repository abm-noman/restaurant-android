package com.example.kutumbari;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderStatusUpdate(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tableTextView.setText("Table: " + order.getTable());
        holder.statusTextView.setText("Status: " + order.getStatus());

        StringBuilder items = new StringBuilder();
        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
            items.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        holder.itemsTextView.setText(items.toString());

        holder.updateStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderStatusUpdate(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tableTextView, statusTextView, itemsTextView;
        Button updateStatusButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tableTextView = itemView.findViewById(R.id.tableTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            updateStatusButton = itemView.findViewById(R.id.updateStatusButton);
        }
    }
}
