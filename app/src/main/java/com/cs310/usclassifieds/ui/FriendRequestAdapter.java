package com.cs310.usclassifieds.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.squareup.picasso.Picasso;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.UserViewHolder> {

    private User[] mDataset;

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView userName;
        private TextView userDescription;
        private ImageView userImage;
        private Button acceptButton;
        private Button rejectButton;
        private UserManager userManager = null;
        private User user;

        // Gets the activity from a view
        private Activity getActivity(View view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity)context;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
            return null;
        }

        public UserViewHolder(View userView) {
            super(userView);
            this.userName = userView.findViewById(R.id.user_name);
            this.userDescription = userView.findViewById(R.id.user_description);
            this.userImage = userView.findViewById(R.id.user_image);
            this.acceptButton = userView.findViewById(R.id.accept_button);
            this.rejectButton = userView.findViewById(R.id.reject_button);
            this.userManager = new UserManager(new DataManager());
            final MainActivity mainActivity = (MainActivity) getActivity(userView);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userManager.acceptFriendRequest(mainActivity.getCurrentUser(), user);
                    acceptButton.setText("Accepted");
                    acceptButton.setEnabled(false);
                    rejectButton.setEnabled(false);
                }
            });

            rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userManager.declineFriendRequest(mainActivity.getCurrentUser(), user);
                    rejectButton.setText("Rejected");
                    rejectButton.setEnabled(false);
                    acceptButton.setEnabled(false);
                }
            });


            userView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.v("USER " + this.userName, " was clicked");
            // Get activity from view
            MainActivity activity = (MainActivity) this.getActivity(view);

            // Pass item to activity
            activity.setViewedUser(this.user);

            // Redirect to item page
            Navigation.findNavController(view).navigate(R.id.navigation_contact);
        }
    }

    public FriendRequestAdapter(User[] dataset) {
        this.mDataset = dataset;
    }

    @Override
    public FriendRequestAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.listed_friend_request, parent, false);

        UserViewHolder ivh = new UserViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.userName.setText(mDataset[position].username);
        final String url = mDataset[position].imageUrl;
        if(url == null) {
            holder.userImage.setImageResource(R.drawable.mystery_item); // Hardcode images for now
        } else {
            Picasso.with(holder.itemView.getContext()).load(url).into(holder.userImage);
        }

        holder.userDescription.setText(mDataset[position].profileDescription);
        holder.user = mDataset[position];
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
