package com.cs310.usclassifieds.ui.createListing;

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

public class CreateListingFragment extends Fragment {

    private CreateListingViewModel mViewModel;

    public static CreateListingFragment newInstance() {
        return new CreateListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(CreateListingViewModel.class);
        View view = inflater.inflate(R.layout.create_listing_fragment, container, false);

        Button submitListingButton = (Button) view.findViewById(R.id.submit_listing_button);
        submitListingButton.setOnClickListener(new View.OnClickListener(){
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
        mViewModel = ViewModelProviders.of(this).get(CreateListingViewModel.class);
        // TODO: Use the ViewModel
    }

}
