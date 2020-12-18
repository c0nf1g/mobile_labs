package iot.mobile.presentation.SharedPreferens;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class SharedPrefs {
    private SharedPreferences prefs;

    private static final String LANGUAGE_KEY = "selected_language";
    private static final String USER_KEY = "current_user_email";

    public void init(FragmentActivity activity) {
        prefs = activity.getSharedPreferences("app_data", Context.MODE_PRIVATE);
    }

    public void setLanguage(String language) {
        prefs.edit().putString(LANGUAGE_KEY, language).apply();
    }

    public String getLanguage() {
        return prefs.getString(LANGUAGE_KEY, "us");
    }

    public void setUser(String user_email) {
        prefs.edit().putString(USER_KEY, user_email).apply();
    }

    public String getUser() {
        return prefs.getString(USER_KEY, "");
    }

    public void removeUser() {
        prefs.edit().remove(USER_KEY).apply();
    }
}
