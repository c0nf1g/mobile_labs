package iot.mobile.presentation;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import iot.mobile.BuildConfig;
import iot.mobile.R;
import iot.mobile.presentation.SharedPreferens.SharedPrefs;
import iot.mobile.presentation.activities.StartScreenActivity;
import iot.mobile.presentation.listeners.ConnectionChangeCallback;
import iot.mobile.presentation.listeners.NetworkChangeListener;
import iot.mobile.presentation.listeners.SignInListener;
import iot.mobile.presentation.listeners.SignUpListener;
import iot.mobile.presentation.fragments.SignInFragment;
import iot.mobile.presentation.fragments.SignUpFragment;
import iot.mobile.presentation.helpers.LanguageManager;
import iot.mobile.presentation.helpers.SharedPrefsManager;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        SignInListener,
        SignUpListener {

    public static final String UA_LANGUAGE = "ua";
    public static final String US_LANGUAGE = "us";
    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;
    private final Configuration configuration = new Configuration();
    private final LanguageManager languageManager = new LanguageManager();
    private final SharedPrefsManager sharedPrefsManager = new SharedPrefsManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defineLanguage();
        setContentView(R.layout.activity_main);
        connectTimber();
        disableBackPressedCallback();
        initFragments();
        checkIsUserLogged();
    }

    private void connectTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void checkIsUserLogged() {
        if (isLogged()) {
            startActivity(new Intent(this, StartScreenActivity.class));
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, signInFragment)
                    .commit();
        }
    }

    private void initFragments() {
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
    }

    private void disableBackPressedCallback() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public void onSignUpNavClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signUpFragment)
                .commit();
    }

    @Override
    public void onSignInClicked() {
        startActivity(new Intent(this, StartScreenActivity.class));
    }

    @Override
    public void onSignUpClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signInFragment)
                .commit();
    }

    @Override
    public void onSignInNavClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signInFragment)
                .commit();
    }

    private void defineLanguage() {
        SharedPrefs sharedPrefs = sharedPrefsManager.initSharedPrefs(this);
        String language = sharedPrefs.getLanguage();

        if (language.equals(UA_LANGUAGE)) {
            languageManager.setLanguage(UA_LANGUAGE, configuration, this);
        } else if (language.equals(US_LANGUAGE)) {
            sharedPrefs.setLanguage(US_LANGUAGE);
        }
    }

    private boolean isLogged() {
        SharedPrefs sharedPrefs = sharedPrefsManager.initSharedPrefs(this);
        String userEmail = sharedPrefs.getUser();

        return userEmail.length() != 0;
    }
}