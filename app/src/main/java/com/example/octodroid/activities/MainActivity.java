package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.adapters.HottestRepositoryAdapter;
import com.example.octodroid.data.GitHub;
import com.example.octodroid.data.SessionPrefs;
import com.example.octodroid.data.SessionPrefsSchema;
import com.example.octodroid.views.ProfileView;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.User;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends RxAppCompatActivity {
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation_drawer)
    ProfileView navigationDrawerView;
    @Bind(R.id.hottest_repository_list)
    RecyclerView hottestRepositoryListView;

    private ActionBarDrawerToggle drawerToggle;

    private HottestRepositoryAdapter hottestRepositoryAdapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViews();
    }

    @Override
    public void onDestroy() {
        if (hottestRepositoryAdapter != null) {
            hottestRepositoryAdapter.destroy();
        }
        super.onDestroy();
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
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
            case R.id.action_search:
                SearchResultActivity.launch(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupViews() {
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        navigationDrawerView.setup();

        hottestRepositoryAdapter = new HottestRepositoryAdapter(hottestRepositoryListView);
        hottestRepositoryListView.setAdapter(hottestRepositoryAdapter);

        requestProfile();
    }

    private void requestProfile() {
        SessionPrefs prefs = SessionPrefsSchema.create(this);
        if (prefs.hasUsername() && prefs.hasPassword()) {
            GitHub.client().authorization(prefs.getUsername(), prefs.getPassword());
            GitHub.client().user()
                    .cache()
                    .compose(this.<Response<User>>bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(r -> {
                        if (!r.isSuccessful()) {
                            return;
                        }
                        navigationDrawerView.setUser(r.entity());
                    });
        } else {
            navigationDrawerView.setUser(null);
        }

    }
}
