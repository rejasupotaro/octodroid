package com.example.octodroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.GitHubClient;
import com.rejasupotaro.octodroid.models.Repository;

import rx.android.app.AppObservable;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends ActionBarActivity {
    private CompositeSubscription subscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GitHubClient client = GitHub.client();
        client.authorization("rejasupotaro", "xxxxx");
        client.cache(this);
        subscription.add(AppObservable.bindActivity(this, client.user())
                .map(r -> r.entity())
                .subscribe(user -> Log.e("debugging", user.toJson())));

        subscription.add(AppObservable.bindActivity(this, client.searchRepositories("android", "stars", "desc"))
                .map(r -> r.entity())
                .subscribe(searchResult -> {
                    for (Repository repository : searchResult.getItems()) {
                        Log.e("debugging", repository.getName());
                    }
                }));
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
