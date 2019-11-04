package com.cs310.usclassifieds.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Item[] mDataset;

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemName;
        private TextView itemDescription;
        private ImageView itemImage;
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
            this.itemName = itemView.findViewById(R.id.item_title);
            this.itemDescription = itemView.findViewById(R.id.item_description);
            this.itemImage = itemView.findViewById(R.id.item_image);
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

    public ItemAdapter(Item[] dataset) {
        this.mDataset = dataset;
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.listed_item, parent, false);

        ItemViewHolder ivh = new ItemViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.itemName.setText(mDataset[position].title);
        holder.itemDescription.setText(mDataset[position].description);
        holder.itemImage.setImageResource(R.drawable.mystery_item); // Hardcode images for now
        holder.item = mDataset[position];
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
