package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.SessionPrefs;
import com.example.octodroid.SessionPrefsSchema;
import com.example.octodroid.adapters.HottestRepositoryAdapter;
import com.example.octodroid.views.ProfileView;
import com.rejasupotaro.octodroid.GitHub;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.navigation_drawer)
    ProfileView navigationDrawerView;
    @InjectView(R.id.hottest_repository_list)
    RecyclerView hottestRepositoryListView;

    private ActionBarDrawerToggle drawerToggle;

    private Subscription subscription = Subscriptions.empty();

    private HottestRepositoryAdapter hottestRepositoryAdapter;

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
            GitHub.client().authorization(prefs.getUsername(), prefs.getPassword());
            ButterKnife.inject(this);
            setupViews();
        } else {
            LoginActivity.launch(this);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
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
        subscription = GitHub.client().user()
                .cache()
                .subscribe(r -> {
                    if (!r.isSuccessful()) {
                        return;
                    }
                    navigationDrawerView.setUser(r.entity());
                });
    }
}
