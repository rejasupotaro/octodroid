package com.example.octodroid.views.helpers;

import android.content.Context;
import android.widget.Toast;

import com.example.octodroid.R;

public class ToastHelper {
    public static void showError(Context context) {
        Toast.makeText(context, R.string.message_error, Toast.LENGTH_SHORT).show();
    }

    public static void showLoginFailed(Context context) {
        Toast.makeText(context, R.string.message_error_login_failed, Toast.LENGTH_SHORT).show();
    }
}
