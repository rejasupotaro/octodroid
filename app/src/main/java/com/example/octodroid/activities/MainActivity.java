package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.octodroid.R;
import com.example.octodroid.data.SessionManager;
import com.example.octodroid.data.prefs.OctodroidPrefs;
import com.example.octodroid.data.prefs.OctodroidPrefsSchema;
import com.example.octodroid.fragments.RepositoryEventListFragment;
import com.example.octodroid.fragments.RepositoryEventListFragmentAutoBundle;
import com.example.octodroid.intent.RequestCode;
import com.example.octodroid.views.components.ViewPagerAdapter;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.Resource;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.repository_view_pager_tabs)
    TabLayout tabLayout;
    @Bind(R.id.repository_view_pager)
    ViewPager viewPager;
    @Bind(R.id.empty_view_container)
    View emptyViewContainer;

    private List<Repository> repositories = new ArrayList<>();

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            Repository repository = repositories.get(position);
            setTitle(repository);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (SessionManager.isLoggedIn(this)) {
            SessionManager.login(this);
            setupViews();
        } else {
            LoginActivity.launch(this);
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
            case android.R.id.home:
                return true;
            case R.id.action_add_repository:
                RepositoryListActivity.launch(this);
                return true;
            case R.id.action_logout:
                SessionManager.logout(this);
                LoginActivity.launch(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCode.ADD_REPOSITORY:
                setupViews();
                break;
            default:
                // do nothing
                break;
        }
    }

    private void setTitle(Repository repository) {
        getSupportActionBar().setTitle(repository.getFullName());
    }

    private void setupViews() {
        OctodroidPrefs octodroidPrefs = OctodroidPrefsSchema.get(this);
        if (octodroidPrefs.getSeletedSerializedRepositories().isEmpty()) {
            emptyViewContainer.setVisibility(View.VISIBLE);

            setTitle("");
        } else {
            repositories.clear();

            emptyViewContainer.setVisibility(View.GONE);

            ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            for (String serializedRepositories : octodroidPrefs.getSeletedSerializedRepositories()) {
                Repository repository = Resource.fromJson(serializedRepositories, Repository.class);

                RepositoryEventListFragment fragment = RepositoryEventListFragmentAutoBundle
                        .createFragmentBuilder(repository)
                        .build();

                pagerAdapter.addFragment(fragment, repository.getName());
                repositories.add(repository);
            }

            viewPager.setAdapter(pagerAdapter);
            viewPager.addOnPageChangeListener(onPageChangeListener);
            tabLayout.setupWithViewPager(viewPager);

            setTitle(repositories.get(0));
        }
    }

    @OnClick(R.id.add_repository_button)
    void onAddRepositoryButtonClick() {
        RepositoryListActivity.launch(this);
    }
}
