package iot.mobile.presentation.helpers;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageManager {
    public void setLanguage(String language, Configuration configuration, Context context) {
        String country = language.toUpperCase();
        Locale locale = new Locale(language, country);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
