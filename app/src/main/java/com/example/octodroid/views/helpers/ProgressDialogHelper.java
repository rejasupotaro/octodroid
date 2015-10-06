package com.example.octodroid.views.helpers;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.octodroid.R;

public class ProgressDialogHelper {

    private ProgressDialog progressDialog;
    private String message;

    public ProgressDialogHelper(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            message = context.getString(R.string.message_loading);
        }
    }

    public synchronized void show() {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public synchronized void dismiss() {
        if (progressDialog == null) {
            return;
        }

        progressDialog.dismiss();
    }
}

