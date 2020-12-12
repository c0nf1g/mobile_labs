package iot.mobile.presentation.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import iot.mobile.R;
import iot.mobile.presentation.fragments.NewsFragment;

public class StartScreenActivity extends AppCompatActivity {
    private NewsFragment newsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        newsFragment = new NewsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.start_activity_container, newsFragment)
                .commit();
    }
}