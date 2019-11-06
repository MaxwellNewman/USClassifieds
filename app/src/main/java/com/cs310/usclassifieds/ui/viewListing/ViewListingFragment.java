package com.cs310.usclassifieds.ui.viewListing;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;


public class ViewListingFragment extends Fragment implements OnMapReadyCallback{

    private ViewListingViewModel mViewModel;
    private GoogleMap googleMap;
    private MapView mMapView;
    private Item item;

    public static ViewListingFragment newInstance() {
        return new ViewListingFragment();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        this.googleMap = mMap;
        LatLng itemLoc = new LatLng(this.item.location.latitude,
                this.item.location.longitude);
        googleMap.addMarker(new MarkerOptions().position(itemLoc).title(
                item.title).snippet(item.description));

        // Zoom automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(itemLoc).zoom(18).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ViewListingViewModel.class);
        View view = inflater.inflate(R.layout.view_listing_fragment, container, false);

        // Item that is being listed
        MainActivity activity = (MainActivity) getActivity();
        this.item = activity.getViewedItem();

        // Display name of item
        TextView itemName = view.findViewById(R.id.viewed_item_name);
        TextView itemPrice = view.findViewById(R.id.viewed_item_price);
        TextView itemDescription = view.findViewById(R.id.viewed_item_description);
        TextView itemListingUser = view.findViewById(R.id.viewed_item_listingUser);
        ImageView itemImage = view.findViewById(R.id.viewed_item_image);

        itemName.setText(item.title);

        DecimalFormat df = new DecimalFormat("#.00");
        String price = df.format(item.price);
        itemPrice.setText("Price: $" + price);

        itemDescription.setText(item.description);
        itemListingUser.setText("Username: " + item.username);

        final String url = item.imageUrl == null ?
                MainActivity.DEFAULT_URL :
                item.imageUrl;
        Picasso.with(getContext()).load(url).into(itemImage);

        // Display item on map
        this.mMapView = view.findViewById(R.id.map_view_listing);
        this.mMapView.onCreate(savedInstanceState);

        this.mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewListingViewModel.class);
        // TODO: Use the ViewModel
    }

}
