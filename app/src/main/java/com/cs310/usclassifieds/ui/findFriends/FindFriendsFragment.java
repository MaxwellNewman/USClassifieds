package com.cs310.usclassifieds.ui.findFriends;

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
import android.widget.EditText;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.SearchManager;

import java.util.List;

public class FindFriendsFragment extends Fragment implements View.OnClickListener{

    private FindFriendsViewModel mViewModel;
    private EditText searchText;
    private SearchManager searchManager = new SearchManager(new DataManager());
    public static FindFriendsFragment newInstance() {
        return new FindFriendsFragment();
    }

    @Override
    public void onClick(View view) {
        //TODO (btw you need to do it for all of them, I'm not about to make a million todos)
        String searchText = this.searchText.getText().toString();
        List<User> users = searchManager.searchUsers(searchText);

        MainActivity activity = (MainActivity) getActivity();
        activity.passUsers(users);

//        Navigation.findNavController(view).navigate(R.id.navigation_friend_results);
        Navigation.findNavController(view).navigate(R.id.action_navigation_friends_to_navigation_friend_results);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(FindFriendsViewModel.class);
        View view = inflater.inflate(R.layout.find_friends_fragment, container, false);
        Button searchFriendsbutton = (Button) view.findViewById(R.id.search_friends_button);
        this.searchText = (EditText) view.findViewById(R.id.searchbar);
        searchFriendsbutton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FindFriendsViewModel.class);
        // TODO: Use the ViewModel
    }

}

