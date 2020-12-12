package iot.mobile.presentation;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import iot.mobile.BuildConfig;
import iot.mobile.R;
import iot.mobile.presentation.activities.StartScreenActivity;
import iot.mobile.presentation.callbacks.SignInListener;
import iot.mobile.presentation.callbacks.SignUpListener;
import iot.mobile.presentation.fragments.SignInFragment;
import iot.mobile.presentation.fragments.SignUpFragment;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        SignInListener,
        SignUpListener {

    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        getSupportFragmentManager().beginTransaction()
            .add(R.id.container, signInFragment)
            .commit();
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
}