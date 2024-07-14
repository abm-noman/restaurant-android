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

public class ManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerViewManagerOrders;
    private ManagerOrderAdapter managerOrderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        recyclerViewManagerOrders = findViewById(R.id.recyclerViewManagerOrders);
        recyclerViewManagerOrders.setLayoutManager(new LinearLayoutManager(this));
        managerOrderAdapter = new ManagerOrderAdapter(orderList);
        recyclerViewManagerOrders.setAdapter(managerOrderAdapter);

        ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        loadCompletedOrders();
    }

    private void loadCompletedOrders() {
        ordersRef.orderByChild("status").equalTo("completed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    orderList.add(order);
                }
                managerOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManagerActivity.this, "Failed to load orders.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
