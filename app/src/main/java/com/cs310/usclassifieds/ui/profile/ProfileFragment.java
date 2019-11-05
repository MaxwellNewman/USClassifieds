package com.cs310.usclassifieds.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel mViewModel;
    private UserManager userManager = new UserManager(new DataManager());
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public void onClick(View view) {
        MainActivity activity = (MainActivity) getActivity();
        List<User> friends = userManager.getFriendsOf(activity.getCurrentUserId());
        if(friends == null) {
            friends = new ArrayList<User>();
        }

        activity.passUsers(friends);

//        Navigation.findNavController(view).navigate(R.id.navigation_friend_results);
        Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_friend_results2);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        final User currentUser = activity.getCurrentUser();

        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        Button viewFriendsButton = (Button) view.findViewById(R.id.view_friends_button);
        viewFriendsButton.setOnClickListener(this);

        Button listingsButton = (Button) view.findViewById(R.id.listings_button);
        listingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Need to create new fragment for this, view_listing currently views a listing from search
                //Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
                //TODO load the users listings
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_results2);
            }
        });

        Button findFriendsButton = (Button) view.findViewById(R.id.find_friends_button);
        findFriendsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.navigation_find_friends);
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_friends);
            }
        });

        ImageView imageView = (ImageView)view.findViewById(R.id.profile_image_view);

        final String url = currentUser.imageUrl == null ?
                MainActivity.DEFAULT_URL :
                currentUser.imageUrl;

        Picasso.with(getContext()).load(url).into(imageView);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
