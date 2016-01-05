package com.example.octodroid.views.helpers

import android.content.Context
import android.widget.Toast

import com.example.octodroid.R

object ToastHelper {
    fun showError(context: Context) {
        Toast.makeText(context, R.string.message_error, Toast.LENGTH_SHORT).show()
    }

    fun showLoginFailed(context: Context) {
        Toast.makeText(context, R.string.message_error_login_failed, Toast.LENGTH_SHORT).show()
    }
}
