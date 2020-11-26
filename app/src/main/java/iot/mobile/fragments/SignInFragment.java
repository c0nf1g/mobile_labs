package iot.mobile.fragments;

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
import iot.mobile.viewModels.SignInViewModel;

public class SignInFragment extends Fragment {
    private SignInViewModel signInViewModel;
    private OnSignUpNavClickedListener onSignUpNavClickedListener;
    private OnSignInClickedListener onSignInClickedListener;
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

    public interface OnSignUpNavClickedListener {
        public void onSignUpNavClicked();
    }

    public interface OnSignInClickedListener {
        public void onSignInClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            onSignUpNavClickedListener = (OnSignUpNavClickedListener) context;
            onSignInClickedListener = (OnSignInClickedListener) context;
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
        signInViewModel.getIsUserExist().observe(this, isUserExist -> {
            if (!isUserExist) {
                showToast("User with this email doesn't exist!");
            }
        });
    }

    private void checkIsLoggedIn() {
        signInViewModel.getIsLoggedIn().observe(this, isLoggedIn -> {
            if (isLoggedIn) {
                showAlertDialog("Login is successful!");
            } else {
                showToast("Could not sign in!");
            }
        });
    }

    private void checkIsCredentialWrong() {
        signInViewModel.getIsCredentialWrong().observe(this, isCredentialWrong -> {
            if (isCredentialWrong) {
                showToast("Email or password are incorrect");
            }
        });
    }

    private void checkInputFields() {
        signInViewModel.getLiveData().observe(this, liveDataMap -> {
            textInputLayoutEmailAddress.setErrorEnabled(false);
            textInputLayoutPassword.setErrorEnabled(false);

            if (!liveDataMap.get("isValid")) {
                if (!liveDataMap.get("isValidEmail")) {
                    textInputLayoutEmailAddress.setError("Please enter a valid email!");
                }
                if (!liveDataMap.get("isValidPassword")) {
                    textInputLayoutPassword.setError("Password is incorrect");
                }
            }
        });
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle("Info");
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