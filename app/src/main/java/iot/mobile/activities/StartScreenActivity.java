package iot.mobile.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import iot.mobile.R;
import iot.mobile.fragments.StartScreenFragment;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.startScreenContainer, new StartScreenFragment())
                .commit();
    }
}