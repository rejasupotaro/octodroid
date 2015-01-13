package com.example.octodroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.example.octodroid.R;
import com.example.octodroid.views.MoreLoadScrollListener;
import com.example.octodroid.adapters.RepositoryAdapter;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.SearchResult;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

import static com.rejasupotaro.octodroid.http.Order.DESC;
import static com.rejasupotaro.octodroid.http.Sort.STARS;
import static rx.android.app.AppObservable.*;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.repository_list)
    RecyclerView repositoryListView;

    private RepositoryAdapter repositoryAdapter;
    private CompositeSubscription subscription = new CompositeSubscription();
    private BehaviorSubject<Observable<Response<SearchResult>>> responseSubject;
    private Observable<Response<SearchResult>> pagedResponse;
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
        ButterKnife.inject(this);
        setupViews();
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

    private void setupViews() {
        GitHub.client().cache(this);
        subscription.add(bindActivity(this, GitHub.client().hottestRepositories())
                .map(Response::entity)
                .subscribe(searchResult -> {
                    repositoryAdapter.clear();
                    repositoryAdapter.addRepositories(searchResult.getItems());
                }));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        repositoryAdapter = new RepositoryAdapter(repositoryListView, layoutManager);
        repositoryListView.setAdapter(repositoryAdapter);
        repositoryListView.setOnScrollListener(new MoreLoadScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (pagedResponse != null) {
                    responseSubject.onNext(pagedResponse);
                }
            }
        });
    }

    private void submit(String query) {
        if (TextUtils.isEmpty(query)) {
            return;
        }

        repositoryAdapter.clear();

        responseSubject = BehaviorSubject.create(GitHub.client().searchRepositories(query, STARS, DESC));
        subscription.add(bindActivity(this, responseSubject)
                .flatMap(r -> r)
                .subscribe(r -> {
                    if (r.entity().getItems() == null || r.entity().getItems().isEmpty()) {
                        return;
                    }

                    List<Repository> repositories = r.entity().getItems();
                    repositoryAdapter.addRepositories(repositories);

                    pagedResponse = r.next();
                }));
    }
}
