package com.example.octodroid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.intent.RequestCode;
import com.example.octodroid.views.adapters.RepositoryAdapter;
import com.yatatsu.autobundle.Arg;
import com.yatatsu.autobundle.AutoBundle;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoryListActivity extends AppCompatActivity {
    @Bind(R.id.repository_list)
    RecyclerView repositoryListView;

    private RepositoryAdapter repositoryAdapter;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RepositoryListActivity.class);
        activity.startActivityForResult(intent, RequestCode.ADD_REPOSITORY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
        ButterKnife.bind(this);
        AutoBundle.bind(this);

        setupActionBar();
        setupViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        repositoryAdapter.saveSelectedRepositories();
        super.onBackPressed();
    }

    public void setupActionBar() {
        getSupportActionBar().setTitle(getString(R.string.title_repository_list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViews() {
        repositoryAdapter = new RepositoryAdapter(repositoryListView);
        repositoryListView.setAdapter(repositoryAdapter);
    }
}
