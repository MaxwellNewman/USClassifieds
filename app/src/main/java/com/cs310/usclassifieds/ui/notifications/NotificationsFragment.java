package com.cs310.usclassifieds.ui.notifications;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.ui.FriendRequestAdapter;
import com.cs310.usclassifieds.ui.UserAdapter;
import com.cs310.usclassifieds.ui.friendResults.FriendResultsFragment;
import com.cs310.usclassifieds.ui.friendResults.FriendResultsViewModel;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.notifications_fragment, container, false);

        this.recyclerView = (RecyclerView) view.findViewById(R.id.friend_requests_view);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(this.layoutManager);

        // Get item list
        MainActivity mainActivity = (MainActivity) getActivity();
        final User currentUser = mainActivity.getCurrentUser();
        final List<User> users = mainActivity.getUsers();
        this.mAdapter = new FriendRequestAdapter(users.toArray(new User[users.size()]));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        // TODO: Use the ViewModel
    }
}
