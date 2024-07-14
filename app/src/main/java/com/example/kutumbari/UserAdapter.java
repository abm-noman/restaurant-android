package com.example.kutumbari;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private OnUserRoleChangeListener listener;

    public interface OnUserRoleChangeListener {
        void onRoleChange(User user, String newRole);
    }

    public UserAdapter(List<User> userList, OnUserRoleChangeListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());
        holder.roleTextView.setText("Role: " + user.getRole());

        holder.changeRoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newRole = getNextRole(user.getRole());
                listener.onRoleChange(user, newRole);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, roleTextView;
        Button changeRoleButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            roleTextView = itemView.findViewById(R.id.roleTextView);
            changeRoleButton = itemView.findViewById(R.id.changeRoleButton);
        }
    }

    private String getNextRole(String currentRole) {
        switch (currentRole) {
            case "waiter":
                return "chef";
            case "chef":
                return "manager";
            case "manager":
                return "admin";
            case "admin":
                return "waiter";
            default:
                return "waiter";
        }
    }
}
