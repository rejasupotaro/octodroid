package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.SessionPrefs;
import com.example.octodroid.SessionPrefsSchema;
import com.example.octodroid.adapters.HottestRepositoryAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.hottest_repository_list)
    RecyclerView hottestRepositoryListView;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionPrefs prefs = SessionPrefsSchema.get(this);
        if (prefs.hasUsername() && prefs.hasPassword()) {
            ButterKnife.inject(this);
            setupViews();
        } else {
            LoginActivity.launch(this);
            finish();
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
            case R.id.action_search:
                SearchResultActivity.launch(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViews() {
        HottestRepositoryAdapter hottestRepositoryAdapter = new HottestRepositoryAdapter(hottestRepositoryListView);
        hottestRepositoryListView.setAdapter(hottestRepositoryAdapter);
    }
}
