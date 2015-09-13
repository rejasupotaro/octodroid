package com.example.octodroid.views.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.octodroid.data.GitHub;
import com.example.octodroid.views.components.MoreLoadScrollListener;
import com.example.octodroid.views.helpers.ToastHelper;
import com.example.octodroid.views.holders.ProgressViewHolder;
import com.example.octodroid.views.holders.RepositoryItemViewHolder;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.http.params.Params;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.Subscriptions;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SearchResultAdapter.class.getSimpleName();

    private static class ViewType {
        private static final int ITEM = 1;
        private static final int FOOTER = 2;
    }

    private RecyclerView recyclerView;
    private List<Repository> repositories = new ArrayList<>();
    private Subscription subscription = Subscriptions.empty();
    private BehaviorSubject<Observable<Response<SearchResult<Repository>>>> responseSubject;
    private Observable<Response<SearchResult<Repository>>> pagedResponse;

    public SearchResultAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setOnScrollListener(new MoreLoadScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (pagedResponse != null) {
                    responseSubject.onNext(pagedResponse);
                }
            }
        });
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.FOOTER) {
            return ProgressViewHolder.create(parent);
        } else {
            return RepositoryItemViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == ViewType.FOOTER) {
            // do nothing
        } else {
            Repository repository = repositories.get(position);
            ((RepositoryItemViewHolder) viewHolder).bind(repository);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (repositories.size() == 0 || position == repositories.size()) {
            return ViewType.FOOTER;
        } else {
            return ViewType.ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size() + 1;
    }

    public void clear() {
        repositories.clear();
        notifyDataSetChanged();
    }

    public void submit(String query) {
        clear();
        recyclerView.setVisibility(View.VISIBLE);

        Params params = new Params()
                .add("q", query);
        responseSubject = BehaviorSubject.create(GitHub.client().searchRepositories(params));
        subscription = responseSubject
                .flatMap(r -> r)
                .subscribe(new ResponseSubscriber());
    }

    public void destroy() {
        subscription.unsubscribe();
    }

    private void showError() {
        ToastHelper.showError(recyclerView.getContext());
    }

    private class ResponseSubscriber extends Subscriber<Response<SearchResult<Repository>>> {

        @Override
        public void onCompleted() {
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.toString());
            showError();
        }

        @Override
        public void onNext(Response<SearchResult<Repository>> r) {
            if (r.entity().getItems() == null || r.entity().getItems().isEmpty()) {
                return;
            }

            List<Repository> items = r.entity().getItems();
            int startPosition = repositories.size();
            repositories.addAll(items);
            notifyItemRangeInserted(startPosition, items.size());

            pagedResponse = r.next();
        }
    }
}