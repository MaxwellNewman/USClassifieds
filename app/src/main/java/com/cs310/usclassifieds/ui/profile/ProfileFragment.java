package com.cs310.usclassifieds.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.SearchManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.cs310.usclassifieds.ui.ItemAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView usernameText;
    private TextView emailText;
    private Button findFriendsButton;
    private Button viewFriendsButton;
    private Button listingsButton;
    private Button notificationsButton;
    private ImageView imageView;

    private SearchManager searchManager = new SearchManager(new DataManager());
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ProfileViewModel mViewModel;
    private UserManager userManager = new UserManager(new DataManager());
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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

        usernameText = view.findViewById(R.id.username_profile);
        emailText = view.findViewById(R.id.email_profile);
        findFriendsButton = view.findViewById(R.id.find_friends_button);
        viewFriendsButton = view.findViewById(R.id.view_friends_button);
        listingsButton = view.findViewById(R.id.listings_button);
        notificationsButton = view.findViewById(R.id.notifcations_button);

        usernameText.append(currentUser.username);
        emailText.append(currentUser.contactInfo.email);

        this.recyclerView = (RecyclerView) view.findViewById(R.id.browse_user_listings_view);
        this.layoutManager = new LinearLayoutManager((getActivity()));
        this.recyclerView.setLayoutManager(this.layoutManager);

//        MainActivity activity = (MainActivity) getActivity();
//        List<Item> items = searchManager.searchItemsByTitle(this.searchText.getText().toString());
        List<Item> items = searchManager.searchItemsByUser(currentUser.username);
        activity.passItems(items);
        Log.v("ITEMS FOUND:", "" + items.size());

        this.mAdapter = new ItemAdapter(items.toArray(new Item[items.size()]));
        recyclerView.setAdapter(mAdapter);

        viewFriendsButton.setOnClickListener(this);

        listingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Need to create new fragment for this, view_listing currently views a listing from search
                //Navigation.findNavController(view).navigate(R.id.navigation_view_listing);
                //TODO load the users listings
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_results2);
            }
        });

        findFriendsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.navigation_find_friends);
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_friends);
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_notifications);
            }
        });

        imageView = view.findViewById(R.id.profile_image_view);

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
