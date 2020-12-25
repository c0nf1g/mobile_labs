package iot.mobile.presentation.callbacks;

import iot.mobile.presentation.helpers.AlertManager;
import iot.mobile.presentation.listeners.ConnectionChangeCallback;

public class MyNetworkChangeCallback implements ConnectionChangeCallback {
    private AlertManager alertManager;

    public MyNetworkChangeCallback(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    @Override
    public void onConnectionsStatusChange(boolean isConnected) {
        if (!isConnected) {
            alertManager.showAlertDialog(
                    "Failed to connect",
                    "No internet connection!"
            );
        }
    }
}
