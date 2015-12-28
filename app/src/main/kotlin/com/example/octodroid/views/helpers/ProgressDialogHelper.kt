package com.example.octodroid.views.helpers

import android.app.ProgressDialog
import android.content.Context

import com.example.octodroid.R

class ProgressDialogHelper(context: Context) {

    private var progressDialog: ProgressDialog? = null
    private var message: String? = null

    init {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context)
            message = context.getString(R.string.message_loading)
        }
    }

    @Synchronized fun show() {
        progressDialog!!.setMessage(message)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.show()
    }

    @Synchronized fun dismiss() {
        if (progressDialog == null) {
            return
        }

        progressDialog!!.dismiss()
    }
}

