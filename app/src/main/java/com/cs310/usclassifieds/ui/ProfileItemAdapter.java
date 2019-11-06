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
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.ItemManager;
import com.squareup.picasso.Picasso;

public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ItemViewHolder> {

    private Item[] mDataset;

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemName;
        private TextView itemDescription;
        private ImageView itemImage;
        private Button sellButton;
        private Item item;

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

        public ItemViewHolder(View itemView) {
            super(itemView);
            final MainActivity mainActivity = (MainActivity) getActivity(itemView);
            this.itemName = itemView.findViewById(R.id.item_title);
            this.itemDescription = itemView.findViewById(R.id.item_description);
            this.itemImage = itemView.findViewById(R.id.item_image);
            this.sellButton = itemView.findViewById(R.id.sell_item_button);

            sellButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemManager itemManager = new ItemManager(new DataManager());
                    itemManager.markSold(item, mainActivity.getCurrentUser());

                    sellButton.setText("Sold");
                    sellButton.setEnabled(false);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.v("ITEM " + this.itemName, " was clicked");
            // Get activity from view
            MainActivity activity = (MainActivity) this.getActivity(view);

            // Pass item to activity
            activity.setViewedItem(this.item);

            // Redirect to item page
            Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
        }
    }

    public ProfileItemAdapter(Item[] dataset) {
        this.mDataset = dataset;
    }

    @Override
    public ProfileItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.profile_listed_item, parent, false);

        ItemViewHolder ivh = new ItemViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.itemName.setText(mDataset[position].title);
        holder.itemDescription.setText(mDataset[position].description);

        final String url = mDataset[position].imageUrl;
        if(url == null) {
            holder.itemImage.setImageResource(R.drawable.mystery_item); // Hardcode images for now
        } else {
            Picasso.with(holder.itemView.getContext()).load(url).into(holder.itemImage);
        }
        holder.item = mDataset[position];
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
