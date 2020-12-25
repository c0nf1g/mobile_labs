package iot.mobile.presentation.listeners;

import android.content.Context;
import android.net.ConnectivityManager;

import iot.mobile.presentation.callbacks.NetworkChangeCallback;

public class NetworkChangeListener {
    private final ConnectivityManager connectivityManager;
    private final NetworkChangeCallback networkChangeCallback;

    public NetworkChangeListener(Context context, ConnectionChangeCallback changeCallback) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkChangeCallback = new NetworkChangeCallback(changeCallback);
    }

    public void subscribe() {
        connectivityManager.registerDefaultNetworkCallback(networkChangeCallback);
    }

    public void unsubscribe() {
        connectivityManager.unregisterNetworkCallback(networkChangeCallback);
    }
}
