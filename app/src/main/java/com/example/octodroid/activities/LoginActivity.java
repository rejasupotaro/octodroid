package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.octodroid.R;
import com.example.octodroid.SessionPrefs;
import com.example.octodroid.SessionPrefsSchema;
import com.example.octodroid.views.helpers.ProgressDialogHelper;
import com.example.octodroid.views.helpers.ToastHelper;
import com.rejasupotaro.octodroid.GitHub;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.username_edit_text)
    EditText usernameEditText;
    @Bind(R.id.password_edit_text)
    EditText passwordEditText;

    private Subscription subscription = Subscriptions.empty();
    private ProgressDialogHelper progressDialogHelper;

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupActionBar();
        setupViews();
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.login_button)
    void onLoginButtonClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }

        login(username, password);
    }

    private void login(String username, String password) {
        progressDialogHelper.show();
        GitHub.client().authorization(username, password);
        subscription = GitHub.client().user()
                .subscribe(r -> {
                    progressDialogHelper.dismiss();
                    if (r.isSuccessful()) {
                        loginSucceeded(username, password);
                    } else {
                        ToastHelper.showLoginFailed(this);
                    }
                }, throwable -> {
                    progressDialogHelper.dismiss();
                    ToastHelper.showError(this);
                });
    }

    private void loginSucceeded(String username, String password) {
        SessionPrefs prefs = SessionPrefsSchema.create(this);
        prefs.putUsername(username);
        prefs.putPassword(password);

        MainActivity.launch(this);
        finish();
    }

    private void setupViews() {
        progressDialogHelper = new ProgressDialogHelper(this);
    }
}
