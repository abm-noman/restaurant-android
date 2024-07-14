package com.example.kutumbari;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChefActivity extends AppCompatActivity implements ChefOrderAdapter.OnOrderClickListener {

    private RecyclerView recyclerViewChefOrders;
    private ChefOrderAdapter chefOrderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        recyclerViewChefOrders = findViewById(R.id.recyclerViewChefOrders);
        recyclerViewChefOrders.setLayoutManager(new LinearLayoutManager(this));
        chefOrderAdapter = new ChefOrderAdapter(orderList, this);
        recyclerViewChefOrders.setAdapter(chefOrderAdapter);

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
                chefOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChefActivity.this, "Failed to load orders.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOrderConfirm(Order order) {
        // Update the order status to "confirmed"
        ordersRef.child(order.getId()).child("status").setValue("confirmed")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChefActivity.this, "Order confirmed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChefActivity.this, "Failed to confirm order", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onOrderComplete(Order order) {
        // Update the order status to "completed"
        ordersRef.child(order.getId()).child("status").setValue("completed")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChefActivity.this, "Order completed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChefActivity.this, "Failed to complete order", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
