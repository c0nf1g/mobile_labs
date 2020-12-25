package iot.mobile.presentation.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessaging;

import iot.mobile.R;
import iot.mobile.presentation.SharedPreferens.SharedPrefs;
import iot.mobile.presentation.MainActivity;
import iot.mobile.presentation.callbacks.MyNetworkChangeCallback;
import iot.mobile.presentation.helpers.AlertManager;
import iot.mobile.presentation.listeners.ConnectionChangeCallback;
import iot.mobile.presentation.listeners.NetworkChangeListener;
import iot.mobile.presentation.listeners.NewsListener;
import iot.mobile.presentation.listeners.ProfileListener;
import iot.mobile.presentation.fragments.NewsFragment;
import iot.mobile.presentation.fragments.ProfileFragment;
import iot.mobile.presentation.helpers.LanguageManager;
import iot.mobile.presentation.helpers.SharedPrefsManager;
import iot.mobile.presentation.uiData.NewsViewData;
import timber.log.Timber;

public class StartScreenActivity extends AppCompatActivity implements NewsListener, ProfileListener {
    public static final String UA_LANGUAGE = "ua";
    public static final String US_LANGUAGE = "us";
    private final Configuration configuration = new Configuration();
    private final LanguageManager languageManager = new LanguageManager();
    private final SharedPrefsManager sharedPrefsManager = new SharedPrefsManager();
    private NewsFragment newsFragment;
    private ProfileFragment profileFragment;
    private AlertManager alertManager = new AlertManager(this);
    private MyNetworkChangeCallback myNetworkChangeCallback = new MyNetworkChangeCallback(alertManager);
    private NetworkChangeListener networkChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        initFirebaseMessaging();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        networkChangeListener = new NetworkChangeListener(this, myNetworkChangeCallback);
        networkChangeListener.subscribe();
        disableBackPressedCallback();
        initFragments();
        startNewsFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkChangeListener.unsubscribe();
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

    private void initFirebaseMessaging() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.e(task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();
                    Timber.i(token);
                });
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
            getApplicationContext().getResources().updateConfiguration(configuration, null);
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