package com.example.octodroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.adapters.SearchResultAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchResultActivity extends ActionBarActivity {
    @InjectView(R.id.repository_list)
    RecyclerView searchResultListView;

    private SearchResultAdapter searchResultAdapter;
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (!TextUtils.isEmpty(query)) {
                searchResultAdapter.submit(query);
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SearchResultActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.inject(this);
        setupViews();
    }

    @Override
    public void onDestroy() {
        searchResultAdapter.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_result, menu);

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
        searchResultAdapter = new SearchResultAdapter(searchResultListView);
        searchResultListView.setAdapter(searchResultAdapter);
    }
}
