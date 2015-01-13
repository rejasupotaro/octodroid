package com.example.octodroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.octodroid.R;
import com.example.octodroid.adapters.HottestRepositoryAdapter;
import com.example.octodroid.adapters.SearchResultAdapter;
import com.rejasupotaro.octodroid.GitHub;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.hottest_repository_list)
    RecyclerView hottestRepositoryListView;
    @InjectView(R.id.repository_list)
    RecyclerView searchResultListView;

    private HottestRepositoryAdapter hottestRepositoryAdapter;
    private SearchResultAdapter searchResultAdapter;
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (TextUtils.isEmpty(query)) {
                hottestRepositoryListView.setVisibility(View.VISIBLE);
                searchResultListView.setVisibility(View.GONE);
            } else {
                hottestRepositoryListView.setVisibility(View.GONE);
                searchResultListView.setVisibility(View.VISIBLE);
                searchResultAdapter.submit(query);
            }
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
        ButterKnife.inject(this);
        setupViews();
    }

    @Override
    public void onDestroy() {
        hottestRepositoryAdapter.destroy();
        searchResultAdapter.destroy();
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

    private void setupViews() {
        GitHub.client().cache(this);

        hottestRepositoryAdapter = new HottestRepositoryAdapter(hottestRepositoryListView);
        hottestRepositoryListView.setAdapter(hottestRepositoryAdapter);

        searchResultAdapter = new SearchResultAdapter(searchResultListView);
        searchResultListView.setAdapter(searchResultAdapter);
    }
}
