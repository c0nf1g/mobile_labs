package iot.mobile.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.HashMap;
import java.util.Map;

import iot.mobile.presentation.validators.EmailValidator;
import iot.mobile.presentation.validators.ICredentialValidator;
import iot.mobile.presentation.validators.PasswordValidator;
import timber.log.Timber;

public class SignUpViewModel extends ViewModel {
    private final ICredentialValidator emailValidator = new EmailValidator();
    private final ICredentialValidator passwordValidator = new PasswordValidator();
    private final MutableLiveData<Map<String, Boolean>> liveData = new MutableLiveData<Map<String, Boolean>>();
    private final MutableLiveData<Boolean> isSignedUp = new MutableLiveData<>();
    private final Map<String, Boolean> liveDataMap = new HashMap<String, Boolean>();
    private final MutableLiveData<Boolean> isUserExist = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private boolean isValidEmail;
    private boolean isValidPasswordOne;
    private boolean isValidPasswordTwo;
    private boolean isPassTwoEqualToPassOne;
    private boolean isValid;

    public void signUp(String name, String email, String passwordOne,
                       String passwordTwo) {
        receiveCredentials(email, passwordOne, passwordTwo);

        isValid = validateCredentials();
        if (isValid) {
            firebaseSignUp(email, passwordOne);
        }

        createLiveDataMap();
    }

    private void createLiveDataMap() {
        liveDataMap.put("isValidEmail", isValidEmail);
        liveDataMap.put("isValidPassOne", isValidPasswordOne);
        liveDataMap.put("isValidPassTwo", isValidPasswordTwo);
        liveDataMap.put("isEqualPassOneToPassTwo", isPassTwoEqualToPassOne);
        liveDataMap.put("isValid", isValid);
        liveData.setValue(liveDataMap);
    }

    private void receiveCredentials(String email, String passwordOne, String passwordTwo) {
        isValidEmail = emailValidator.isValid(email);
        isValidPasswordOne = passwordValidator.isValid(passwordOne);
        isValidPasswordTwo = passwordValidator.isValid(passwordTwo);
        isPassTwoEqualToPassOne = passwordTwo.equals(passwordOne);
    }

    private boolean validateCredentials() {
        if (!isValidEmail) {
            Timber.e("Incorrect email");
            return false;
        }
        if (!isValidPasswordOne) {
            Timber.e("Invalid Password");
            return false;
        }
        if (!isValidPasswordTwo) {
            Timber.e("Invalid Password");
            return false;
        }
        if (!isPassTwoEqualToPassOne) {
            Timber.e("Passwords are not the same");
            return false;
        }
        return true;
    }

    private void firebaseSignUp(String email, String passwordOne) {
        firebaseAuth.createUserWithEmailAndPassword(email, passwordOne)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isSignedUp.setValue(true);
                        Timber.d("Signed up");
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthUserCollisionException emailExist) {
                            Timber.e("User with this email already exist!");
                            isUserExist.setValue(true);
                        } catch (Exception e) {
                            Timber.e(e.toString());
                            isSignedUp.setValue(false);
                        }
                    }
                });
    }

    public MutableLiveData<Map<String, Boolean>> getLiveData() {
        return liveData;
    }

    public MutableLiveData<Boolean> getIsSignedUp() {
        return isSignedUp;
    }

    public MutableLiveData<Boolean> getIsUserExist() {
        return isUserExist;
    }
}
