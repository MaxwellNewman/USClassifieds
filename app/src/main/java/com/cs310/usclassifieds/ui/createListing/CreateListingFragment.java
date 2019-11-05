package com.cs310.usclassifieds.ui.createListing;

import androidx.fragment.app.FragmentHostCallback;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.ItemManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CreateListingFragment extends Fragment {

    private CreateListingViewModel mViewModel;
    private EditText titleText;
    private EditText priceText;
    private AutocompleteSupportFragment locText;
    private EditText descText;
    private Button uploadButton;
    private Button submitButton;
    private TextView locationText;
    private EditText tagsText;

    private Uri mImageUri;
    private Place locationInfo;
    private static final String API_KEY_PATH = "ApiKey";
    private static final int PICK_IMAGE_REQUEST = 1;

    private ItemManager itemManager = new ItemManager(new DataManager());
    private UserManager userManager = new UserManager(new DataManager());

    public static CreateListingFragment newInstance() {
        return new CreateListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(CreateListingViewModel.class);
        View view = inflater.inflate(R.layout.create_listing_fragment, container, false);

        titleText = view.findViewById(R.id.create_listing_title_input);
        priceText = view.findViewById(R.id.create_listing_price_input);
        locText = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.create_listing_location_input);
        descText = view.findViewById(R.id.create_listing_description_input);
        uploadButton = view.findViewById(R.id.upload_photos_button);
        submitButton = view.findViewById(R.id.submit_listing_button);
        locationText = view.findViewById(R.id.location_text);
        tagsText = view.findViewById(R.id.tags_input);

        final String apiKey = getApiKey();

        Places.initialize(getContext(), apiKey);
        Places.createClient(view.getContext());

        locText.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS));
        locText.setOnPlaceSelectedListener(new PlaceSelectionListener() {
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

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
                try {
                    Thread.sleep(1000);
                    uploadButton.setText("Image Uploaded");
                    uploadButton.setEnabled(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean validInputs = checkInputValidity();

                if (validInputs) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setViewedItem(uploadListing());
                    Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
                }
            }
        });

        return view;
    }

    private String getApiKey() {
        return "AIzaSyCSTWld6jstN2eosUB6MYCTgjs8qYK-lm8";
    }

    private boolean checkInputValidity() {
        if (titleText.getText().toString().matches("")) {
            Toast.makeText(getActivity(), "You did not enter an item title", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mImageUri == null) {
            Toast.makeText(getActivity(), "You did not select an image", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (locationInfo == null) {
            Toast.makeText(getActivity(), "You did not select a location", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private List<String> getTags() {
        List<String> tagsList = new ArrayList<>();
        String tagsTextString = tagsText.getText().toString().replaceAll("\\s+","");
        String[] tags = tagsTextString.split(",");

        for (String s : tags) {
            tagsList.add(s.toLowerCase());
        }

        return tagsList;
    }

    private Item uploadListing() {
        Item item = new Item();
        item.title = titleText.getText().toString();
        item.description = descText.getText().toString();
        if (!priceText.getText().toString().matches("")) {
            item.price = Float.valueOf(priceText.getText().toString());
        }
        item.imageUri = mImageUri;
        item.imageUrl = null;
        MainActivity activity =(MainActivity) getActivity();
        User currentUser = userManager.loadProfile(activity.getCurrentUserId());
        item.userId = currentUser.userId;
        item.username = currentUser.username;
        item.location.address = locationInfo.getAddress();
        item.location.latitude = locationInfo.getLatLng().latitude;
        item.location.longitude = locationInfo.getLatLng().longitude;

        item.tags = getTags();

        itemManager.createListing(item);
        return item;
    }

    private void selectFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null
                && data.getData() != null) {
            mImageUri = data.getData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreateListingViewModel.class);
        // TODO: Use the ViewModel
    }
}
