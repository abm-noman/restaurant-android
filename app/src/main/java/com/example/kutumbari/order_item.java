package com.example.kutumbari;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements OrderAdapter.OnOrderClickListener {

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderList, this);
        recyclerViewOrders.setAdapter(orderAdapter);

        ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        loadOrders();
    }

    private void loadOrders() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    orderList.add(order);
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderActivity.this, "Failed to load orders.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOrderStatusUpdate(Order order) {
        // Update the order status here. For example, change status to "completed".
        ordersRef.child(order.getId()).child("status").setValue("completed")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(OrderActivity.this, "Order status updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OrderActivity.this, "Failed to update order status", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
