package com.cs310.usclassifieds.ui.profile;

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

public class ProfileFragment extends Fragment {//} implements View.OnClickListener {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        Button viewFriendsButton = (Button) view.findViewById(R.id.view_friends_button);
        viewFriendsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_friend_results);
            }
        });

        Button listingsButton = (Button) view.findViewById(R.id.listings_button);
        listingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
            }
        });

        Button findFriendsButton = (Button) view.findViewById(R.id.find_friends_button);
        findFriendsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_friends);
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
