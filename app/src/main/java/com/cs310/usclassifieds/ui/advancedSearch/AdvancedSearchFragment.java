package com.cs310.usclassifieds.ui.advancedSearch;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.SearchManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.resolveSizeAndState;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdvancedSearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private AdvancedSearchViewModel mViewModel;
    private Button advancedSearchButton;
    private Spinner lowToHighSpinner;
    private SearchManager searchManager = new SearchManager(new DataManager());
    private EditText searchText;
    private EditText userText;
    private AutocompleteSupportFragment locationText;
    private Place locationInfo = null;

    private boolean searchByUser = false;
    private boolean searchByCost = false;
    private boolean searchByDistance = false;

    @Override
    public void onClick(View view) {

        // Didn't select a location yet
        if (searchByDistance && locationInfo == null) {
            // Do nothing for now
            Log.v("Ended", "here");
        }
        else {
            // Pass correct items to main activity
            MainActivity activity = (MainActivity) getActivity();
            List<Item> items;
            String search = searchText.getText().toString();

            if (searchByCost) {
                boolean cheapestFirst = lowToHighSpinner.getSelectedItem().toString(
                ).equals("Low to High");

                items = searchManager.searchItemsByTitleOrTags(search);
                items = searchManager.sortByPrice(cheapestFirst, items);
                activity.passItems(items);

            } else if (searchByUser) {

                items = searchManager.searchItemsByUserAndTitle(userText.getText().toString(), search);
                activity.passItems(items);

            } else if (searchByDistance) {
                items = searchManager.searchItemsByTitleOrTags(search);
                LatLng loc = locationInfo.getLatLng();
                List<Pair<Double, Item>> distancesAndItems;
                distancesAndItems = searchManager.sortByDistance(loc.latitude, loc.longitude, items);
                items.clear();
                List<Double> distances = new ArrayList<Double>();
                for (Pair<Double, Item> distanceItem : distancesAndItems) {
                    distances.add(distanceItem.first);
                    items.add(distanceItem.second);
                }

                activity.passItems(items);
                //activity.passDistances(distances);
            }

            Navigation.findNavController(view).navigate(R.id.navigation_results);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String optionChosen = (String) parent.getItemAtPosition(pos).toString();
        Log.v("OPTION CHOSEN:", optionChosen);
        if (optionChosen.equals("Search by Distance")) {
            // Have address to input
            userText.setVisibility(View.GONE);
            searchByUser = false;
            lowToHighSpinner.setVisibility(View.GONE);
            searchByCost = false;
            locationText.getView().setVisibility(View.VISIBLE);
            searchByDistance = true;
        } else if (optionChosen.equals("Search by Cost")) {
            // Select between low-to-high and high-to-low
            userText.setVisibility(View.GONE);
            searchByUser = false;
            lowToHighSpinner.setVisibility(View.VISIBLE);
            searchByCost = true;
            locationText.getView().setVisibility(View.GONE);
            searchByDistance = false;
        } else { // Search by User
            userText.setVisibility(View.VISIBLE);
            searchByUser = true;
            lowToHighSpinner.setVisibility(View.GONE);
            searchByCost = false;
            locationText.getView().setVisibility(View.GONE);
            searchByDistance = false;
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
        this.lowToHighSpinner = (Spinner) view.findViewById(R.id.advanced_low_vs_high);
        this.searchText = (EditText) view.findViewById(R.id.advanced_search_bar);
        this.userText = (EditText) view.findViewById(R.id.advanced_user_search);
        this.locationText = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.advanced_location);
        locationText.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS));

        final String apiKey = getApiKey();

        Places.initialize(getContext(), apiKey);
        Places.createClient(view.getContext());

        this.locationText.setHint("Filter by location");

        locationText.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationInfo = place;
                locationText.setHint(place.getAddress());

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

        ArrayAdapter<CharSequence> costAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.cost_options, android.R.layout.simple_spinner_dropdown_item);
        this.lowToHighSpinner.setAdapter(costAdapter);


        advancedSearchButton.setOnClickListener(this);

        return view;
    }

    private String getApiKey() {
        return "AIzaSyCSTWld6jstN2eosUB6MYCTgjs8qYK-lm8";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdvancedSearchViewModel.class);
        // TODO: Use the ViewModel
    }

}

