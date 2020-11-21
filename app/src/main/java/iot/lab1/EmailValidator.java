package iot.lab1;

import android.util.Patterns;
import java.util.regex.Matcher;

public class EmailValidator {
    public boolean validate(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }
}
