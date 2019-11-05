package com.cs310.usclassifieds.ui.contact;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs310.usclassifieds.MainActivity;
import com.cs310.usclassifieds.R;
import com.cs310.usclassifieds.model.datamodel.User;
import com.squareup.picasso.Picasso;

public class ContactFragment extends Fragment {

    private ContactViewModel mViewModel;
    private User user;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);

        MainActivity activity = (MainActivity) getActivity();

        // User that fragment is looking at
        user = activity.getViewedUser();
        TextView contactName = view.findViewById(R.id.contact_name);
        TextView username = view.findViewById(R.id.username_profile);
        TextView email = view.findViewById(R.id.email_profile);
        ImageView profilePicture = view.findViewById(R.id.profile_image_view);

        contactName.setText(user.fullName);
        username.append(user.username);
        email.append(user.contactInfo.email);
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
