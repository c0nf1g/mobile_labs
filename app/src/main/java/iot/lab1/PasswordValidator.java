package iot.lab1;

public class PasswordValidator {
    public boolean validate(String password) {
        int checkValue = 8;
        return password.length() > checkValue;
    }
}

