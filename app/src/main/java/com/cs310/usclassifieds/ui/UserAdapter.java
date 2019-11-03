package com.cs310.usclassifieds.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private User[] mDataset;

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userDescription;
        public ImageView userImage;
        public UserViewHolder(View userView) {
            super(userView);
            this.userName = userView.findViewById(R.id.user_name);
            this.userDescription = userView.findViewById(R.id.user_description);
            this.userImage = userView.findViewById(R.id.user_image);
        }
    }

    public UserAdapter(User[] dataset) {
        this.mDataset = dataset;
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.listed_user, parent, false);

        UserViewHolder ivh = new UserViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.userName.setText(mDataset[position].username);
        holder.userDescription.setText("Example user description");
        holder.userImage.setImageResource(R.drawable.mystery_item); // Hardcode images for now
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
