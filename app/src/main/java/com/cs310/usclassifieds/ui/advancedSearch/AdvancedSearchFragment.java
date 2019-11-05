package com.cs310.usclassifieds.ui.advancedSearch;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.cs310.usclassifieds.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdvancedSearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private AdvancedSearchViewModel mViewModel;
    private Button advancedSearchButton;
    private Switch lowToHighSwitch;
    private EditText searchText;
    private EditText userText;
    private AutocompleteSupportFragment locationText;
    private Place locationInfo;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String optionChosen = (String) parent.getItemAtPosition(pos).toString();
        Log.v("OPTION CHOSEN:", optionChosen);
        if (optionChosen.equals("Search by Distance")) {
            // Have address to input
            userText.setVisibility(View.GONE);
            lowToHighSwitch.setVisibility(View.GONE);
            locationText.getView().setVisibility(View.VISIBLE);



        } else if (optionChosen.equals("Search by Cost")) {
            // Select between low-to-high and high-to-low
            userText.setVisibility(View.GONE);
            lowToHighSwitch.setVisibility(View.VISIBLE);
            locationText.getView().setVisibility(View.GONE);
        } else { // Search by User
            userText.setVisibility(View.VISIBLE);
            lowToHighSwitch.setVisibility(View.GONE);
            locationText.getView().setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static AdvancedSearchFragment newInstance() {
        return new AdvancedSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AdvancedSearchViewModel.class);
        View view = inflater.inflate(R.layout.advanced_search_fragment, container, false);

        this.advancedSearchButton = (Button) view.findViewById(R.id.submit_advanced_search_button);
        this.lowToHighSwitch = (Switch) view.findViewById(R.id.advanced_low_vs_high);
        this.searchText = (EditText) view.findViewById(R.id.advanced_search_bar);
        this.userText = (EditText) view.findViewById(R.id.advanced_user_search);
        this.locationText = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.advanced_location);

        locationText.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS));
        locationText.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationInfo = place;
                locationText.setText(place.getAddress());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        Spinner spinner = (Spinner) view.findViewById(R.id.advanced_search_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.advanced_search_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        advancedSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO call the database and pass data
                //TODO (btw you need to do it for all of them, I'm not about to make a million todos)
                Navigation.findNavController(view).navigate(R.id.navigation_results);
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

