package com.cs310.usclassifieds.ui.results;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs310.usclassifieds.R;

public class ResultsFragment extends Fragment {

    private ResultsViewModel mViewModel;

    public static ResultsFragment newInstance() {
        return new ResultsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        View view = inflater.inflate(R.layout.results_fragment, container, false);

        Button loadListingButton = (Button) view.findViewById(R.id.placeholder_load_listing_button);
        loadListingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO call the database and pass data
                //TODO (btw you need to do it for all of them, I'm not about to make a million todos)
                Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        // TODO: Use the ViewModel
    }

}
