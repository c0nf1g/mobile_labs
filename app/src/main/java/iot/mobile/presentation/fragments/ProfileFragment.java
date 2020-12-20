package iot.mobile.presentation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import iot.mobile.R;
import iot.mobile.presentation.callbacks.NewsListener;
import iot.mobile.presentation.callbacks.ProfileListener;
import iot.mobile.presentation.viewModels.ProfileViewModel;

public class ProfileFragment extends Fragment {
    ProfileViewModel profileViewModel;
    private TextInputLayout inputLayoutPassword;
    private TextInputEditText editTextPassword;
    private Button languageButton;
    private Button submitButton;
    private ProfileListener onLanguageClickedListener;
    private ProfileListener onToolbarClickedListener;
    private ProfileListener onLogoutClickedListener;
    private Toolbar profileToolbar;
    private Button logoutButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

        try {
            onLanguageClickedListener = (ProfileListener) context;
            onToolbarClickedListener = (ProfileListener) context;
            onLogoutClickedListener = (ProfileListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement one of ProfileLister methods");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLanguageClickedListener = null;
        onToolbarClickedListener = null;
        onLogoutClickedListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        initUI(profileView);
        registerViewModel();

        return profileView;
    }

    private void initUI(View profileView) {
        inputLayoutPassword = profileView.findViewById(R.id.inputLayoutPassword);
        editTextPassword = profileView.findViewById(R.id.editTextPassword);
        languageButton = profileView.findViewById(R.id.language_button);
        submitButton = profileView.findViewById(R.id.submit);
        profileToolbar = profileView.findViewById(R.id.toolbarProfile);
        logoutButton = profileView.findViewById(R.id.logout_button);

        languageButton.setOnClickListener(view -> {
            onLanguageClickedListener.onLanguageClick();
        });

        submitButton.setOnClickListener(view -> {
            profileViewModel.updatePassword(editTextPassword.getText().toString());
        });

        profileToolbar.setNavigationOnClickListener(view -> {
            onToolbarClickedListener.onToolbarClick();
        });

        logoutButton.setOnClickListener(view -> {
            onLogoutClickedListener.onLogoutClick();
        });
    }

    private void registerViewModel() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        getIsPasswordChanged();
        getError();
    }

    private void getError() {
        profileViewModel.getError().observe(getViewLifecycleOwner(), this::showToast);
    }

    private void getIsPasswordChanged() {
        profileViewModel.getIsPasswordChanged().observe(getViewLifecycleOwner(), isPasswordChanged -> {
            if (isPasswordChanged) {
                showToast("Password is changed");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}