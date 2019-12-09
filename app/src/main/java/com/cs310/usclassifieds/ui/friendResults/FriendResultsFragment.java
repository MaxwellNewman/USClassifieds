package com.cs310.usclassifieds.ui.friendResults;

import androidx.lifecycle.ViewModelProviders;

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

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.ui.results.ResultsViewModel;
import com.cs310.usclassifieds.ui.UserAdapter;

import java.util.List;

public class FriendResultsFragment extends Fragment {

    private FriendResultsViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static FriendResultsFragment newInstance() {
        return new FriendResultsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(FriendResultsViewModel.class);
        View view = inflater.inflate(R.layout.friend_results_fragment, container, false);

        this.recyclerView = (RecyclerView) view.findViewById(R.id.users_view);
        this.layoutManager = new LinearLayoutManager(getActivity());

        this.recyclerView.setLayoutManager(this.layoutManager);

        // Get item list
        MainActivity activity = (MainActivity) getActivity();
        List<User> users = activity.getUsers();

        if(users != null) {
            for(User user : users) {
                if(user.userId.equalsIgnoreCase(activity.getCurrentUser().userId)) {
                    users.remove(user);
                    break;
                }
            }
        }

        Log.v("FriendResultsFragment", users.toString());
        this.mAdapter = new UserAdapter(users.toArray(new User[users.size()]));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FriendResultsViewModel.class);
        // TODO: Use the ViewModel
    }

}
