package com.cs310.usclassifieds.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.cs310.usclassifieds.ui.ProfileItemAdapter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView fullNameText;
    private TextView usernameText;
    private TextView emailText;
    private TextView salesText;
    private Button findFriendsButton;
    private Button viewFriendsButton;
    private Button listingsButton;
    private Button notificationsButton;
    private ImageView profileImage;
    private ImageView badgeImage;
    private TextView badgeText;
    private TextView descriptionText;

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

        final MainActivity activity = (MainActivity) getActivity();
        final User currentUser = activity.getCurrentUser();

        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        fullNameText = view.findViewById(R.id.name_text);
        usernameText = view.findViewById(R.id.username_profile);
        emailText = view.findViewById(R.id.email_profile);
        findFriendsButton = view.findViewById(R.id.find_friends_button);
        viewFriendsButton = view.findViewById(R.id.view_friends_button);
        notificationsButton = view.findViewById(R.id.notifcations_button);
        salesText = view.findViewById(R.id.sales_text);
        descriptionText = view.findViewById(R.id.profile_description);

        fullNameText.setText(currentUser.fullName);

        usernameText.append(currentUser.username);
        emailText.append(currentUser.contactInfo.email);

        descriptionText.append((currentUser.profileDescription));

        salesText.append(Integer.toString(currentUser.sales));

        this.recyclerView = (RecyclerView) view.findViewById(R.id.browse_user_listings_view);
        this.layoutManager = new LinearLayoutManager((getActivity()));
        this.recyclerView.setLayoutManager(this.layoutManager);

        List<Item> items = searchManager.searchItemsByUser(currentUser.username);
        activity.passItems(items);
        Log.v("ITEMS FOUND:", "" + items.size());

        this.mAdapter = new ProfileItemAdapter(items.toArray(new Item[items.size()]));
        recyclerView.setAdapter(mAdapter);

        viewFriendsButton.setOnClickListener(this);

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
                final List<User> users = userManager.getFriendRequestsOf(activity.getCurrentUser());
                activity.passUsers(users);
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_navigation_notifications);
            }
        });

        profileImage = view.findViewById(R.id.profile_image_view);

        final String url = currentUser.imageUrl == null ?
                MainActivity.DEFAULT_URL :
                currentUser.imageUrl;

        Picasso.with(getContext()).load(url).into(profileImage);

        String badgeUrl = "https://static.turbosquid.com/Preview/2019/08/05__03_59_25/01.jpg0D2E9299-EC9C-49D0-B56B-B6F2454D8006Zoom.jpg";
        badgeImage = view.findViewById(R.id.profile_badge);
        badgeText = view.findViewById(R.id.badgeText);
        if (currentUser.sales >= 3) {
            Picasso.with(getContext()).load(badgeUrl).into(badgeImage);
            badgeText.setText("Top Seller");
        }

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
