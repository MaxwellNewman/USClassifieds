package com.cs310.usclassifieds.ui.map;

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

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.SearchManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private MapViewModel mViewModel;
    private GoogleMap googleMap;
    private MapView mMapView;
    private List<Item> items;
    private SearchManager searchManager = new SearchManager(new DataManager());
    private EditText searchText;
    private Marker selectedMarker = null;
    private View view;
    private Button searchButton;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onClick(View view) {
        //TODO (btw you need to do it for all of them, I'm not about to make a million todos)
        String searchText = this.searchText.getText().toString();
        this.items = searchManager.searchItemsByTitle(searchText);

        // Make loading screen

        // Update map results
        mMapView.getMapAsync(this);
    }

    // When a marker is clicked
    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (selectedMarker == null || !selectedMarker.getPosition().equals(marker.getPosition())) {
            selectedMarker = marker;
            marker.showInfoWindow();
            return false;
        }
        else {
            // Navigate to the listing clicked
            for (Item item : items) {
                // Not necessarily the correct item, but it matches the marker in all fields
                // Can fix this in case 2 people sell the exact same item with the same description
                // in the exact same place (seems unlikely)
                Log.v("ITEM:", item.title);
                if (marker.getTitle().equals(item.title) && marker.getSnippet().equals(
                        item.description) && marker.getPosition().latitude ==
                        item.location.latitude && marker.getPosition().longitude ==
                        item.location.longitude) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setViewedItem(item);
                    Navigation.findNavController(this.view).navigate(R.id.navigation_view_listing);
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        this.googleMap = mMap;
        String searchText = this.searchText.getText().toString();
        Log.v("Map Search Text", searchText);
        googleMap.setOnMarkerClickListener(this);
        googleMap.clear();
        // Place item markers on the map
        for (Item item : items){
            Log.println(Log.VERBOSE, "FOUND", "items" + items.size());
            LatLng itemLatLng = new LatLng(item.location.latitude, item.location.longitude);

            MarkerOptions options = new MarkerOptions().position(itemLatLng).title(
                    item.title).snippet(item.description);

            Marker marker = googleMap.addMarker(options);
            Log.v("MapFragment", "title:" + item.title);
            Log.v("MapFragment", "snippet:" + item.description);
        }

        // Zoom automatically to usc
        LatLng usc = new LatLng(34.0211917, -118.2863514);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(usc).zoom((float)15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        this.view = inflater.inflate(R.layout.map_fragment, container, false);
        this.searchText = (EditText) view.findViewById(R.id.map_search_bar);
        this.searchButton = (Button) view.findViewById(R.id.map_search_button);
        this.searchButton.setOnClickListener(this);

        // Load items from searchText (should default to load all items)

        Log.v("searching for:", searchText.getText().toString());

        this.items = searchManager.searchItemsByTitle(searchText.getText().toString());

        this.mMapView = (MapView) view.findViewById(R.id.map_of_listings);
        this.mMapView.onCreate(savedInstanceState);

        this.mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return this.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }

}

