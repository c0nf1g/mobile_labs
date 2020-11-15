package iot.lab1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public boolean isEmailValid(String email) {
        String template = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(template, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
