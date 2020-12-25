package iot.mobile.presentation.helpers;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class AlertManager {
    private Context context;

    public AlertManager(Context context) {
        this.context = context;
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
