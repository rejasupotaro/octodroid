package com.example.octodroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.models.Repository;

import rx.android.app.AppObservable;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends ActionBarActivity {
    private CompositeSubscription subscription = new CompositeSubscription();
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            submit(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GitHub.client().cache(this);
        subscription.add(AppObservable.bindActivity(this, GitHub.client().hottestRepositories())
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

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getString(R.string.repositories));
        searchView.setOnQueryTextListener(onQueryTextListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit(String query) {
        if (TextUtils.isEmpty(query)) {
            return;
        }

        subscription.add(AppObservable.bindActivity(MainActivity.this, GitHub.client().searchRepositories(query, "stars", "desc"))
                .map(r -> r.entity())
                .subscribe(searchResult -> {
                    for (Repository repository : searchResult.getItems()) {
                        Log.e("debugging", repository.getName());
                    }
                }));
    }
}
