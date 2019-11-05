package com.cs310.usclassifieds.ui.advancedSearch;

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

public class AdvancedSearchFragment extends Fragment {

    private AdvancedSearchViewModel mViewModel;

    public static AdvancedSearchFragment newInstance() {
        return new AdvancedSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AdvancedSearchViewModel.class);
        View view = inflater.inflate(R.layout.advanced_search_fragment, container, false);

        Button submitAdvancedSearchButton = (Button) view.findViewById(R.id.submit_advanced_search_button);
        submitAdvancedSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO call the database and pass data
                //TODO (btw you need to do it for all of them, I'm not about to make a million todos)
//                Navigation.findNavController(view).navigate(R.id.navigation_results);
                Navigation.findNavController(view).navigate(R.id.action_navigation_advanced_search_to_navigation_results);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdvancedSearchViewModel.class);
        // TODO: Use the ViewModel
    }

}

