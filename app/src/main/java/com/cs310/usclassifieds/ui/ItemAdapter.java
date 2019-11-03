package com.cs310.usclassifieds.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Item[] mDataset;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemDescription;
        public ImageView itemImage;
        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemName = itemView.findViewById(R.id.item_title);
            this.itemDescription = itemView.findViewById(R.id.item_description);
            this.itemImage = itemView.findViewById(R.id.item_image);
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
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
