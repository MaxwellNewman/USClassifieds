package com.cs310.usclassifieds.ui.results;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.widget.Button;
import android.widget.TextView;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.ui.ItemAdapter;
import java.util.*;
import android.util.Log;

public class ResultsFragment extends Fragment {

    private ResultsViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ResultsFragment newInstance() {
        return new ResultsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.v("ResultsFragment:", "in onCreateView()");
        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        View view = inflater.inflate(R.layout.results_fragment, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.listings_view);
        this.layoutManager = new LinearLayoutManager(getActivity());

        this.recyclerView.setLayoutManager(this.layoutManager);

        // Get item list
        MainActivity activity = (MainActivity) getActivity();
        List<Item> items = activity.getItems();
        this.mAdapter = new ItemAdapter(items.toArray(new Item[items.size()]));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        // TODO: Use the ViewModel
    }

}
