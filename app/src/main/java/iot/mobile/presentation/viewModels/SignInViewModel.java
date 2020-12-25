package iot.mobile.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.HashMap;
import java.util.Map;

import iot.mobile.presentation.validators.EmailValidator;
import iot.mobile.presentation.validators.ICredentialValidator;
import iot.mobile.presentation.validators.PasswordValidator;
import timber.log.Timber;

public class SignInViewModel extends ViewModel {
    private final ICredentialValidator emailValidator = new EmailValidator();
    private final ICredentialValidator passwordValidator = new PasswordValidator();
    private final MutableLiveData<Map<String, Boolean>> liveData = new MutableLiveData<Map<String, Boolean>>();
    private final Map<String, Boolean> liveDataMap = new HashMap<String, Boolean>();
    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isCredentialWrong = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUserExist = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private boolean isValidEmail;
    private boolean isValidPassword;
    private boolean isValid;

    public void signIn(String email, String password) {
        receiveCredentials(email, password);

        isValid = validateCredentials();
        if (isValid) {
            firebaseSignIn(email, password);
        }

        createLiveDataMap();
    }

    private void createLiveDataMap() {
        liveDataMap.put("isValidEmail", isValidEmail);
        liveDataMap.put("isValidPassword", isValidPassword);
        liveDataMap.put("isValid", isValid);
        liveData.setValue(liveDataMap);
    }

    private void receiveCredentials(String email, String password) {
        isValidEmail = emailValidator.isValid(email);
        isValidPassword = passwordValidator.isValid(password);
    }

    private boolean validateCredentials() {
        if (!isValidEmail) {
            Timber.e("Email is not valid!");
            return false;
        }
        if (!isValidPassword) {
            Timber.e("Password is incorrect!");
            return false;
        }

        return true;
    }

    private void firebaseSignIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isLoggedIn.setValue(true);
                        Timber.d("Logged in");
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidCredentialsException credentialsException) {
                            Timber.e("Email or password is incorrect!");
                            isCredentialWrong.setValue(true);
                        } catch (FirebaseAuthInvalidUserException userException) {
                            Timber.e("Could not find user with this email!");
                            isUserExist.setValue(false);
                        }
                        catch (Exception e) {
                            Timber.e(e.toString());
                            isLoggedIn.setValue(false);
                        }
                    }
                });
    }

    public MutableLiveData<Map<String, Boolean>> getLiveData() {
        return liveData;
    }

    public MutableLiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public MutableLiveData<Boolean> getIsCredentialWrong() {
        return isCredentialWrong;
    }

    public MutableLiveData<Boolean> getIsUserExist() {
        return isUserExist;
    }
}