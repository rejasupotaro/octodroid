package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.data.GitHub;
import com.rejasupotaro.octodroid.models.Repository;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class RepositoryListActivity extends AppCompatActivity {
    private Subscription subscription = Subscriptions.empty();

    public static void launch(Context context) {
        Intent intent = new Intent(context, RepositoryListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
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

    public void setupActionBar() {
        getSupportActionBar().setTitle(getString(R.string.title_repository_list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViews() {
        subscription = GitHub.client().userRepos()
                .subscribe(r -> {
                    for (Repository repository : r.entity()) {
                        Log.d("DEBUG", repository.getName());
                    }
                });
    }
}
