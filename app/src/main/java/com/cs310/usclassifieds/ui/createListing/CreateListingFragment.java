package com.cs310.usclassifieds.ui.createListing;

import androidx.fragment.app.FragmentHostCallback;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.ItemManager;
import com.cs310.usclassifieds.model.manager.UserManager;

import java.util.List;

public class CreateListingFragment extends Fragment {

    private CreateListingViewModel mViewModel;
    private EditText titleText;
    private EditText priceText;
    private EditText locText;
    private EditText descText;
    private Button uploadButton;
    private Button submitButton;

    private Uri mImageUri;
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
        locText = view.findViewById(R.id.create_listing_location_input);
        descText = view.findViewById(R.id.create_listing_description_input);
        uploadButton = view.findViewById(R.id.upload_photos_button);
        submitButton = view.findViewById(R.id.submit_listing_button);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO call the database and pass data
                //TODO (btw you need to do it for all of them, I'm not about to make a million todos)
                uploadListing();
                Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
            }
        });

        return view;
    }

    private void uploadListing() {
        Item item = new Item();
        item.title = titleText.getText().toString().equals("") ? "Test Title" : titleText.getText().toString();
        item.description = descText.getText().toString().equals("") ? "Test Description" : descText.getText().toString();
//        item.location.address = locText.getText().toString();
        item.price = priceText.getText().toString().equals("") ? Float.valueOf("0.0") : Float.valueOf(priceText.getText().toString());
        item.imageUri = mImageUri;
        item.imageUrl = null;
        MainActivity activity =(MainActivity) getActivity();
        User currentUser = userManager.loadProfile(activity.getCurrentUserId());
        item.userId = currentUser.userId;
        item.username = currentUser.username;
        itemManager.createListing(item);
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
