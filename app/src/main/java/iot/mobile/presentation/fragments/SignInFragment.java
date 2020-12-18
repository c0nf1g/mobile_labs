package iot.mobile.presentation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import iot.mobile.R;
import iot.mobile.presentation.SharedPreferens.SharedPrefs;
import iot.mobile.presentation.callbacks.SignInListener;
import iot.mobile.presentation.viewModels.SignInViewModel;

public class SignInFragment extends Fragment {
    private SignInViewModel signInViewModel;
    private SignInListener onSignUpNavClickedListener;
    private SignInListener onSignInClickedListener;
    private TextInputLayout textInputLayoutEmailAddress;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmailAddress;
    private TextInputEditText textInputEditTextPassword;
    private Button signIn;
    private TextView signUpNavigation;

    private final View.OnClickListener onSignInClickListener = view -> {
        String email = textInputEditTextEmailAddress.getText().toString();
        String password = textInputEditTextPassword.getText().toString();

        signInViewModel.signIn(email, password);
    };

    private final View.OnClickListener onSignUpClickListener = view -> {
        onSignUpNavClickedListener.onSignUpNavClicked();
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            onSignUpNavClickedListener = (SignInListener) context;
            onSignInClickedListener = (SignInListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSignUpNavClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onSignUpNavClickedListener = null;
        onSignInClickedListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View signInView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        initUI(signInView);
        signIn.setOnClickListener(onSignInClickListener);
        signUpNavigation.setOnClickListener(onSignUpClickListener);

        registerViewModel();
        checkInputFields();
        checkIsCredentialWrong();
        checkIsLoggedIn();
        checkIsUserExist();

        return signInView;
    }

    private void initUI(View signInView) {
        textInputEditTextEmailAddress = signInView.findViewById(R.id.editTextEmailAddress);
        textInputEditTextPassword = signInView.findViewById(R.id.editTextPassword);
        textInputLayoutEmailAddress = signInView.findViewById(R.id.textInputLayoutEmailAddress);
        textInputLayoutPassword = signInView.findViewById(R.id.textInputLayoutPassword);
        signIn = signInView.findViewById(R.id.buttonSignIn);
        signUpNavigation = signInView.findViewById(R.id.textViewSignUpNavigation);
    }

    private void registerViewModel() {
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    private void checkIsUserExist() {
        signInViewModel.getIsUserExist().observe(getViewLifecycleOwner(), isUserExist -> {
            if (!isUserExist) {
                showToast(getString(R.string.user_exists_error));
            }
        });
    }

    private void checkIsLoggedIn() {
        signInViewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
            if (isLoggedIn) {
                SharedPrefs sharedPrefs = new SharedPrefs();
                sharedPrefs.init(getActivity());
                sharedPrefs.setUser(textInputEditTextEmailAddress.getText().toString());

                showAlertDialog(getString(R.string.login_successfull_message));
            } else {
                showToast("Could not sign in!");
            }
        });
    }

    private void checkIsCredentialWrong() {
        signInViewModel.getIsCredentialWrong().observe(getViewLifecycleOwner(), isCredentialWrong -> {
            if (isCredentialWrong) {
                showToast(getString(R.string.email_or_password_incorrect_error));
            }
        });
    }

    private void checkInputFields() {
        signInViewModel.getLiveData().observe(getViewLifecycleOwner(), liveDataMap -> {
            textInputLayoutEmailAddress.setErrorEnabled(false);
            textInputLayoutPassword.setErrorEnabled(false);

            if (!liveDataMap.get("isValid")) {
                if (!liveDataMap.get("isValidEmail")) {
                    textInputLayoutEmailAddress.setError(getString(R.string.enter_valid_email_message));
                }
                if (!liveDataMap.get("isValidPassword")) {
                    textInputLayoutPassword.setError(getString(R.string.password_incorrect_error));
                }
            }
        });
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle(R.string.info_title);
        builder.setPositiveButton("OK", (dialog, which) -> {
            onSignInClickedListener.onSignInClicked();
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}