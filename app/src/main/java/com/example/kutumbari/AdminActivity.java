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

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers, recyclerViewActivities;
    private UserAdapter userAdapter;
    private ActivityAdapter activityAdapter;
    private List<User> userList = new ArrayList<>();
    private List<Activity> activityList = new ArrayList<>();
    private DatabaseReference usersRef, activitiesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList, new UserAdapter.OnUserRoleChangeListener() {
            @Override
            public void onRoleChange(User user, String newRole) {
                changeUserRole(user, newRole);
            }
        });
        recyclerViewUsers.setAdapter(userAdapter);

        recyclerViewActivities = findViewById(R.id.recyclerViewActivities);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        activityAdapter = new ActivityAdapter(activityList);
        recyclerViewActivities.setAdapter(activityAdapter);

        usersRef = FirebaseDatabase.getInstance().getReference("users");
        activitiesRef = FirebaseDatabase.getInstance().getReference("activities");

        loadUsers();
        loadActivities();
    }

    private void loadUsers() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminActivity.this, "Failed to load users.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadActivities() {
        activitiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activityList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Activity activity = snapshot.getValue(Activity.class);
                    activityList.add(activity);
                }
                activityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminActivity.this, "Failed to load activities.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeUserRole(User user, String newRole) {
        usersRef.child(user.getId()).child("role").setValue(newRole).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AdminActivity.this, "Role updated successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminActivity.this, "Failed to update role.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
