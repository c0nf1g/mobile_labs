package iot.mobile.presentation.validators;

public class PasswordValidator implements ICredentialValidator {
    private final static int PASSWORD_MIN_LENGTH = 8;
    public boolean isValid(String password) {
        return password.length() > PASSWORD_MIN_LENGTH;
    }
}

