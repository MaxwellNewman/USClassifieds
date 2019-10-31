package com.cs310.usclassifieds.ui.advancedSearch;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs310.usclassifieds.R;

public class AdvancedSearchFragment extends Fragment {

    private AdvancedSearchViewModel mViewModel;

    public static AdvancedSearchFragment newInstance() {
        return new AdvancedSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.advanced_search_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdvancedSearchViewModel.class);
        // TODO: Use the ViewModel
    }

}
