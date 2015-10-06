package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.data.SessionManager;
import com.example.octodroid.views.adapters.HottestRepositoryAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.hottest_repository_list)
    RecyclerView hottestRepositoryListView;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (SessionManager.isLoggedIn(this)) {
            SessionManager.login(this);
            setupActionBar();
            setupViews();
        } else {
            LoginActivity.launch(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            case R.id.action_add_repository:
                RepositoryListActivity.launch(this);
                return true;
            case R.id.action_logout:
                SessionManager.logout(this);
                LoginActivity.launch(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupActionBar() {
        getSupportActionBar().setTitle("");
    }

    private void setupViews() {
        HottestRepositoryAdapter hottestRepositoryAdapter = new HottestRepositoryAdapter(hottestRepositoryListView);
        hottestRepositoryListView.setAdapter(hottestRepositoryAdapter);
    }
}
