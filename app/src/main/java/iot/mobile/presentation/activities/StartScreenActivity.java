package iot.mobile.presentation.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Locale;

import iot.mobile.R;
import iot.mobile.presentation.SharedPreferens.SharedPrefs;
import iot.mobile.presentation.MainActivity;
import iot.mobile.presentation.callbacks.NewsListener;
import iot.mobile.presentation.callbacks.ProfileListener;
import iot.mobile.presentation.fragments.NewsFragment;
import iot.mobile.presentation.fragments.ProfileFragment;
import iot.mobile.presentation.helpers.LanguageManager;
import iot.mobile.presentation.helpers.SharedPrefsManager;
import iot.mobile.presentation.uiData.NewsViewData;

public class StartScreenActivity extends AppCompatActivity implements NewsListener, ProfileListener {
    public static final String UA_LANGUAGE = "ua";
    public static final String US_LANGUAGE = "us";
    private final Configuration configuration = new Configuration();
    private final LanguageManager languageManager = new LanguageManager();
    private final SharedPrefsManager sharedPrefsManager = new SharedPrefsManager();
    private NewsFragment newsFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        disableBackPressedCallback();
        initFragments();
        startNewsFragment();
    }

    private void startNewsFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.start_activity_container, newsFragment)
                .commit();
    }

    private void initFragments() {
        newsFragment = new NewsFragment();
        profileFragment = new ProfileFragment();
    }

    private void disableBackPressedCallback() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public void onNewsItemClick(NewsViewData newsViewData) {
        ArticleActivity.start(this, newsViewData);
    }

    @Override
    public void onMyProfileClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.start_activity_container, profileFragment)
                .commit();
    }

    @Override
    public void onLanguageClick() {
        SharedPrefs sharedPrefs = sharedPrefsManager.initSharedPrefs(this);
        String language = sharedPrefs.getLanguage();

        if (language.equals(UA_LANGUAGE)) {
            sharedPrefs.setLanguage(US_LANGUAGE);
            this.getApplicationContext().getResources().updateConfiguration(configuration, null);
        } else if (language.equals(US_LANGUAGE)) {
            sharedPrefs.setLanguage(UA_LANGUAGE);
            languageManager.setLanguage(UA_LANGUAGE, configuration, this);
        }

        finish();
        startActivity(new Intent(this, StartScreenActivity.class));
    }

    @Override
    public void onToolbarClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.start_activity_container, newsFragment)
                .commit();
    }

    @Override
    public void onLogoutClick() {
        SharedPrefs sharedPrefs = new SharedPrefs();
        sharedPrefs.init(this);

        sharedPrefs.removeUser();

        startActivity(new Intent(this, MainActivity.class));
    }
}