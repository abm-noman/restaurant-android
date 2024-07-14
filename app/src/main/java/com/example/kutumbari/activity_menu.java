package com.example.kutumbari;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
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
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class MenuActivity extends AppCompatActivity implements MenuAdapter.OnMenuItemClickListener {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<MenuItem> menuList = new ArrayList<>();
    private DatabaseReference menuRef, ordersRef;
    private Map<String, Integer> currentOrder = new HashMap<>();
    private Button placeOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerView);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(menuList, this);
        recyclerView.setAdapter(menuAdapter);

        menuRef = FirebaseDatabase.getInstance().getReference("menu");
        ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        loadMenuItems();

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
    }

    private void loadMenuItems() {
        menuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MenuItem menuItem = snapshot.getValue(MenuItem.class);
                    menuList.add(menuItem);
                }
                menuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MenuActivity.this, "Failed to load menu items.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAddToOrderClick(MenuItem menuItem) {
        String itemName = menuItem.getName();
        if (currentOrder.containsKey(itemName)) {
            currentOrder.put(itemName, currentOrder.get(itemName) + 1);
        } else {
            currentOrder.put(itemName, 1);
        }
        Toast.makeText(this, itemName + " added to order", Toast.LENGTH_SHORT).show();
    }

    private void placeOrder() {
        int tableNumber = 1; // Replace with actual table number
        Order order = new Order(tableNumber, currentOrder, "pending");
        String orderId = ordersRef.push().getKey();
        ordersRef.child(orderId).setValue(order)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MenuActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                        currentOrder.clear();
                    } else {
                        Toast.makeText(MenuActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
