package com.example.octodroid.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.widget.EditText
import butterknife.bindView
import com.example.octodroid.R
import com.example.octodroid.data.GitHub
import com.example.octodroid.data.prefs.SessionPrefs
import com.example.octodroid.views.helpers.ProgressDialogHelper
import com.example.octodroid.views.helpers.ToastHelper
import rx.subscriptions.Subscriptions

class LoginActivity : AppCompatActivity() {
    val usernameEditText: EditText by bindView(R.id.username_edit_text)
    val passwordEditText: EditText by bindView(R.id.password_edit_text)

    private var subscription = Subscriptions.empty()
    private var progressDialogHelper: ProgressDialogHelper? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupActionBar()
        setupViews()
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun login(username: String, password: String) {
        progressDialogHelper!!.show()
        GitHub.client().authorization(username, password)
        subscription = GitHub.client().user().subscribe({ r ->
            progressDialogHelper!!.dismiss()
            if (r.isSuccessful) {
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
        val prefs = SessionPrefs.get(this)
        prefs.username = username
        prefs.password = password

        MainActivity.launch(this)
        finish()
    }

    private fun setupViews() {
        progressDialogHelper = ProgressDialogHelper(this)

        findViewById(R.id.login_button).setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                return@setOnClickListener
            }

            login(username, password)
        }
    }

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}
