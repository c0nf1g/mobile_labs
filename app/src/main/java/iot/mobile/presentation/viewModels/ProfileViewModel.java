package iot.mobile.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iot.mobile.presentation.validators.ICredentialValidator;
import iot.mobile.presentation.validators.PasswordValidator;
import timber.log.Timber;

public class ProfileViewModel extends ViewModel {
    private final ICredentialValidator passwordValidator = new PasswordValidator();
    private final MutableLiveData<Boolean> isPasswordChanged = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private FirebaseUser user;

    boolean isValidPassword;

    public void updatePassword(String password) {
        validatePassword(password);

        firebaseUpdatePassword(password);
    }

    private void firebaseUpdatePassword(String password) {
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (isValidPassword) {
            if (user != null) {
                user.updatePassword(password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Timber.i("Password updated");
                                isPasswordChanged.setValue(true);
                            } else {
                                Timber.i("Couldn't update password");
                                isPasswordChanged.setValue(false);
                            }
                        });
            }
        } else {
            error.setValue("Incorrect password");
        }
    }

    private void validatePassword(String password) {
        isValidPassword = passwordValidator.isValid(password);
    }

    public MutableLiveData<Boolean> getIsPasswordChanged() {
        return isPasswordChanged;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
