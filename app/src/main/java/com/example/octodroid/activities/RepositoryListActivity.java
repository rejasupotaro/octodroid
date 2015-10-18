package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.views.adapters.RepositoryAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoryListActivity extends AppCompatActivity {
    @Bind(R.id.repository_list)
    RecyclerView repositoryListView;

    private RepositoryAdapter repositoryAdapter;

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
        repositoryAdapter.onDestroy();
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
        repositoryAdapter = new RepositoryAdapter(repositoryListView);
        repositoryListView.setAdapter(repositoryAdapter);
    }
}
