package com.cs310.usclassifieds.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Item[] mDataset;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public ItemViewHolder(TextView itemName) {
            super(itemName);
            this.itemName = itemName;
        }
    }

    public ItemAdapter(Item[] dataset) {
        this.mDataset = dataset;
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // Create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_selectable_list_item, parent, false);

        ItemViewHolder ivh = new ItemViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.itemName.setText(mDataset[position].title);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
