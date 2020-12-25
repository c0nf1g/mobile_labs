package iot.mobile.presentation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import iot.mobile.presentation.listeners.SignUpListener;
import iot.mobile.presentation.viewModels.SignUpViewModel;

public class SignUpFragment extends Fragment {
    private SignUpViewModel signUpViewModel;
    private SignUpListener onSignUpClickedListener;
    private SignUpListener onSignInNavClickedListener;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmailAddress;
    private TextInputLayout textInputLayoutPasswordOne;
    private TextInputLayout textInputLayoutPasswordTwo;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmailAddress;
    private TextInputEditText textInputEditTextPasswordOne;
    private TextInputEditText textInputEditTextPasswordTwo;
    private Button signUp;
    private Toolbar toolbarSignUp;


    private final View.OnClickListener onSignUpClickListener = view -> {
        String name = textInputEditTextName.getText().toString();
        String email = textInputEditTextEmailAddress.getText().toString();
        String passwordOne = textInputEditTextPasswordOne.getText().toString();
        String passwordTwo = textInputEditTextPasswordTwo.getText().toString();

        signUpViewModel.signUp(name, email, passwordOne, passwordTwo);
    };

    private final View.OnClickListener onToolbarSignUpClickListener = view -> {
        onSignInNavClickedListener.onSignInNavClicked();
    };

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            onSignUpClickedListener = (SignUpListener) context;
            onSignInNavClickedListener = (SignUpListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSignUpClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onSignUpClickedListener = null;
        onSignInNavClickedListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View signUpView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initUI(signUpView);

        signUp.setOnClickListener(onSignUpClickListener);
        toolbarSignUp.setNavigationOnClickListener(onToolbarSignUpClickListener);

        registerViewModel();
        checkInputFields();
        checkIsSignedUp();
        checkIsUserExist();

        return signUpView;
    }

    private void initUI(View signUpView) {
        textInputLayoutName = signUpView.findViewById(R.id.textInputLayoutName);
        textInputLayoutEmailAddress = signUpView.findViewById(R.id.textInputLayoutEmailAddress);
        textInputLayoutPasswordOne = signUpView.findViewById(R.id.textInputLayoutPasswordOne);
        textInputLayoutPasswordTwo = signUpView.findViewById(R.id.textInputLayoutPasswordTwo);
        textInputEditTextName = signUpView.findViewById(R.id.editTextName);
        textInputEditTextEmailAddress = signUpView.findViewById(R.id.editTextEmailAddress);
        textInputEditTextPasswordOne = signUpView.findViewById(R.id.editTextPasswordOne);
        textInputEditTextPasswordTwo = signUpView.findViewById(R.id.editTextPasswordTwo);
        signUp = signUpView.findViewById(R.id.buttonSignUp);
        toolbarSignUp = signUpView.findViewById(R.id.toolbarSignUp);
    }

    private void registerViewModel() {
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    private void checkIsUserExist() {
        signUpViewModel.getIsUserExist().observe(getViewLifecycleOwner(), isUserExist -> {
            if (isUserExist) {
                showToast("User with this email already exists!");
            }
        });
    }

    private void checkIsSignedUp() {
        signUpViewModel.getIsSignedUp().observe(getViewLifecycleOwner(), isSignedUp -> {
            if (isSignedUp) {
                showAlertDialog("Registration is successful!");
            } else {
                showToast("Could not sign up!");
            }
        });
    }

    private void checkInputFields() {
        signUpViewModel.getLiveData().observe(getViewLifecycleOwner(), liveDataMap -> {
            textInputLayoutEmailAddress.setErrorEnabled(false);
            textInputLayoutPasswordOne.setErrorEnabled(false);
            textInputLayoutPasswordTwo.setErrorEnabled(false);

            if (!liveDataMap.get("isValid")) {
                if (!liveDataMap.get("isValidEmail")) {
                    textInputLayoutEmailAddress.setError("Please enter a valid email!");
                }
                if (!liveDataMap.get("isValidPassOne")) {
                    textInputLayoutPasswordOne.setError("Password is incorrect!");
                }
                if (!liveDataMap.get("isValidPassTwo")) {
                    textInputLayoutPasswordTwo.setError("Password is incorrect!");
                }
                if (!liveDataMap.get("isEqualPassOneToPassTwo")) {
                    textInputLayoutPasswordTwo.setError("Password did not match!");
                }
            }
        });
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle("Info");
        builder.setPositiveButton("OK", (dialog, which) -> {
            onSignUpClickedListener.onSignUpClicked();
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}