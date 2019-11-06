package com.cs310.usclassifieds.ui.contact;

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
// 7d86339d41028efc4b977c769dae6d6220d22163
import android.widget.ImageView;
import android.widget.TextView;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.UserManager;
// 7d86339d41028efc4b977c769dae6d6220d22163
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment implements View.OnClickListener {

//    private ContactViewModel mViewModel;
//    private User user;
//
//    public static ContactFragment newInstance() {
//        return new ContactFragment();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.contact_fragment, container, false);
//
//        MainActivity activity = (MainActivity) getActivity();
//
//        // User that fragment is looking at
////        user = activity.getViewedUser();
////        TextView contactName = view.findViewById(R.id.contact_name);
////        Log.v("Contact", user.username);
////        contactName.setText(user.username);
//
//
//        return view;
//    }

    private TextView usernameText;
    private TextView emailText;
    private TextView contactNameText;
    private Button viewFriendsButton;
    private Button listingsButton;
    private Button sendFriendRequestButton;
    private ImageView imageView;

    private User user;

    private ContactViewModel mViewModel;
    private UserManager userManager = new UserManager(new DataManager());
    public static ContactFragment newInstance() {
        return new ContactFragment();
    }


    @Override
    public void onClick(View view) {

        MainActivity activity = (MainActivity) getActivity();
        final User currentUser = activity.getCurrentUser();

        List<User> friends = userManager.getFriendsOf(currentUser.userId);
        if(friends == null) {
            friends = new ArrayList<User>();
        }

        activity.passUsers(friends);

        Navigation.findNavController(view).navigate(R.id.action_navigation_contact_to_navigation_friend_results);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        final User currentUser = activity.getCurrentUser();

        mViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        View view = inflater.inflate(R.layout.contact_fragment, container, false);

        // User that fragment is looking at
        user = activity.getViewedUser();
//        TextView contactName = view.findViewById(R.id.contact_name);
//        Log.v("Contact", user.username);
//        contactName.setText(user.username);

        usernameText = view.findViewById(R.id.contact_username_profile);
        emailText = view.findViewById(R.id.contact_email_profile);
        contactNameText = view.findViewById(R.id.contact_name_text);
        TextView email = view.findViewById(R.id.email_profile);
        ImageView profilePicture = view.findViewById(R.id.contact_profile_image_view);

//        findFriendsButton = view.findViewById(R.id.find_friends_button);
        viewFriendsButton = view.findViewById(R.id.contact_find_friends_button);
        listingsButton = view.findViewById(R.id.contact_listings_button);
        sendFriendRequestButton = view.findViewById(R.id.add_friend_button);
        
        contactNameText.setText(user.fullName);
        usernameText.append(user.username);
        emailText.append(user.contactInfo.email);

        viewFriendsButton.setOnClickListener(this);

        listingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Need to create new fragment for this, view_listing currently views a listing from search
                //Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
                //TODO load the users listings
                Navigation.findNavController(view).navigate(R.id.action_navigation_contact_to_navigation_results);
            }
        });

        sendFriendRequestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            //TODO this is where you should send the friend request
                final MainActivity mainActivity = (MainActivity)getActivity();
                final User currentUser = mainActivity.getCurrentUser();
                userManager.sendFriendRequest(currentUser, user);
            }
        });

//        imageView = view.findViewById(R.id.contact_profile_image_view);

        // final String url = currentUser.imageUrl == null ?
        //         MainActivity.DEFAULT_URL :
        //         currentUser.imageUrl;

        // Picasso.with(getContext()).load(url).into(imageView);
        final String url = user.imageUrl == null ?
                MainActivity.DEFAULT_URL :
                user.imageUrl;
        Picasso.with(getContext()).load(url).into(profilePicture);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        // TODO: Use the ViewModel
    }

}
