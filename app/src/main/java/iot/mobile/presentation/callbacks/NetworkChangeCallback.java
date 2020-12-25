package iot.mobile.presentation.callbacks;

import android.net.ConnectivityManager;
import android.net.Network;

import androidx.annotation.NonNull;

import iot.mobile.presentation.listeners.ConnectionChangeCallback;

public class NetworkChangeCallback extends ConnectivityManager.NetworkCallback {
    private final ConnectionChangeCallback changeCallback;

    public NetworkChangeCallback(ConnectionChangeCallback changeCallback) {
        this.changeCallback = changeCallback;
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        changeCallback.onConnectionsStatusChange(true);
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        changeCallback.onConnectionsStatusChange(false);
    }
}
