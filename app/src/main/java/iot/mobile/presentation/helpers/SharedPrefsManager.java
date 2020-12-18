package iot.mobile.presentation.helpers;


import androidx.fragment.app.FragmentActivity;

import iot.mobile.presentation.SharedPreferens.SharedPrefs;

public class SharedPrefsManager {
    public SharedPrefs initSharedPrefs(FragmentActivity activity) {
        SharedPrefs sharedPrefs = new SharedPrefs();
        sharedPrefs.init(activity);

        return sharedPrefs;
    }
}
