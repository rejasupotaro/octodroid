package com.example.octodroid.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.widget.EditText

import com.example.octodroid.R
import com.example.octodroid.data.GitHub
import com.example.octodroid.data.prefs.SessionPrefs
import com.example.octodroid.data.prefs.SessionPrefsSchema
import com.example.octodroid.views.helpers.ProgressDialogHelper
import com.example.octodroid.views.helpers.ToastHelper

import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import rx.Subscription
import rx.subscriptions.Subscriptions

class LoginActivity : AppCompatActivity() {
    @Bind(R.id.username_edit_text)
    internal var usernameEditText: EditText
    @Bind(R.id.password_edit_text)
    internal var passwordEditText: EditText

    private var subscription = Subscriptions.empty()
    private var progressDialogHelper: ProgressDialogHelper? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        setupActionBar()
        setupViews()
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupActionBar() {
        val actionBar = getSupportActionBar()
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
    }

    @OnClick(R.id.login_button)
    internal fun onLoginButtonClick() {
        val username = usernameEditText.getText().toString()
        val password = passwordEditText.getText().toString()
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return
        }

        login(username, password)
    }

    private fun login(username: String, password: String) {
        progressDialogHelper!!.show()
        GitHub.client().authorization(username, password)
        subscription = GitHub.client().user().subscribe({ r ->
            progressDialogHelper!!.dismiss()
            if (r.isSuccessful()) {
                loginSucceeded(username, password)
            } else {
                ToastHelper.showLoginFailed(this)
            }
        }, { throwable ->
            progressDialogHelper!!.dismiss()
            ToastHelper.showError(this)
        })
    }

    private fun loginSucceeded(username: String, password: String) {
        val prefs = SessionPrefsSchema.get(this)
        prefs.putUsername(username)
        prefs.putPassword(password)

        MainActivity.launch(this)
        finish()
    }

    private fun setupViews() {
        progressDialogHelper = ProgressDialogHelper(this)
    }

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
}
