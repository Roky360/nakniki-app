package com.example.nakniki_netflix.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.activities.LoginActivity;
import com.example.nakniki_netflix.activities.UnregisteredHomeActivity;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.repositories.UserRepository;
import com.example.nakniki_netflix.view_models.UserViewModel;
import com.example.nakniki_netflix.view_models.UserViewModelFactory;
import com.example.nakniki_netflix.view_models.ViewModelUtils;
import com.example.nakniki_netflix.widgets.Alert;
import com.example.nakniki_netflix.widgets.AvatarCircle;
import com.example.nakniki_netflix.entities.User;

public class ProfileFragment extends Fragment {

    private LinearLayout avatarContainer;
    private TextView usernameTextView, emailTextView;
    private Button logoutButton;
    private UserViewModel userViewModel;

    private Alert alert;
    private AvatarCircle avatarCircle; // Declare avatarCircle as a class-level variable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        alert = new Alert(requireActivity());
        // Initialize views
        avatarContainer = rootView.findViewById(R.id.avatarContainer);
        usernameTextView = rootView.findViewById(R.id.username);
        emailTextView = rootView.findViewById(R.id.email);
        logoutButton = rootView.findViewById(R.id.editProfileButton);
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(new UserRepository())).get(UserViewModel.class);

        // Add the AvatarCircle to the container
        avatarCircle = new AvatarCircle(getActivity()); // Initialize avatarCircle
        avatarCircle.setAvatarRadius(avatarCircle.dpToPx(80));
        avatarCircle.setAvatarImage(R.drawable.default_avatar);
        avatarContainer.addView(avatarCircle);

        // Set profile data (you can modify this later)
        setProfileData();

        // Handle logout button click
        logoutButton.setOnClickListener(v -> handleLogout());

        return rootView;
    }

    private void setProfileData() {
        LiveData<Resource<User>> live = userViewModel.getUserData();

        ViewModelUtils.observeUntil(live, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS) {
                User user = resource.getData();
                if (user != null) {
                    // if the name is not null, set it to the TextView
                    if (user.getUsername() != null) {
                        usernameTextView.setText(user.getUsername());
                    }

                    // if the email is not null, set it to the TextView
                    if (user.getEmail() != null) {
                        emailTextView.setText(user.getEmail());
                    }
                    Log.e("debug", "Profile picture: " + user.getProfilePic());

                    // if the profile picture is not null, set it to the AvatarCircle
                    if (user.getProfilePic() != null) {
                        Log.e("debug", "Profile picture loaded ");
                        // Extract the avatar name from the profile picture path
                        String profilePicPath = user.getProfilePic();
                        String[] parts = profilePicPath.split("/");

                        // Get the last part ("avatar2.png")
                        String avatarName = parts[parts.length - 1];

                        // Remove the file extension ("avatar2")
                        avatarName = avatarName.substring(0, avatarName.lastIndexOf('.'));
                        Log.e("debug", "Avatar name: " + avatarName);;
                        // search the avatar and put it on the screen
                        int avatarResourceId = getResources().getIdentifier(avatarName, "drawable", getActivity().getPackageName());
                        if (avatarResourceId != 0) {
                            avatarCircle.setAvatarImage(avatarResourceId);
                        } else {
                            avatarCircle.setAvatarImage(R.drawable.default_avatar);
                        }
                    }
                }
            } else {
                showAlert(resource.getMessage(), "error");
            }
       }, resource -> resource.getStatus() == Resource.Status.SUCCESS);
    }

    private void handleLogout() {
        LiveData<Resource<Void>> live = userViewModel.logout();

        ViewModelUtils.observeUntil(live, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS) {
                Intent intent = new Intent(getActivity(), UnregisteredHomeActivity.class);
                startActivity(intent);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.remove(this);
                transaction.commit();
            } else {
                showAlert(resource.getMessage(), "error");
            }
        }, resource -> resource.getStatus() == Resource.Status.SUCCESS);
    }

    private void showAlert(String message, String type) {
        alert.show(message, type);
    }
}

