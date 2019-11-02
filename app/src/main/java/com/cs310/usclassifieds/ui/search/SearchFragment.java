package com.cs310.usclassifieds.ui.search;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import com.cs310.usclassifieds.R;

import com.cs310.usclassifieds.model.manager.SearchManager;
import com.cs310.usclassifieds.model.datamodel.Item;

import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private SearchViewModel mViewModel;
    private EditText searchText;
    private SearchManager searchManager;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onClick(View view) {
        //TODO (btw you need to do it for all of them, I'm not about to make a million todos)
//                Log.v("Tag", searchText.toString());
        String searchText = this.searchText.getText().toString();
        List<Item> items = searchManager.searchItems(searchText);

        //TODO: put the items in the display

        Navigation.findNavController(view).navigate(R.id.navigation_results);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        Button searchButton = (Button) view.findViewById(R.id.search_button);
        this.searchText = (EditText) view.findViewById(R.id.searchbar2);
        searchButton.setOnClickListener(this);

        Button advancedSearchButton = (Button) view.findViewById(R.id.advanced_search_button);
        advancedSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_advanced_search);
            }
        });

        Button mapButton = (Button) view.findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_map);
            }
        });
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel
    }

}
