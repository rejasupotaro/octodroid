package com.example.octodroid.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.octodroid.views.helpers.ToastHelper;
import com.example.octodroid.views.holders.RepositoryItemViewHolder;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.view.ViewObservable;

public class HottestRepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Repository> repositories = new ArrayList<>();

    public HottestRepositoryAdapter(RecyclerView recyclerView) {
        this.context = recyclerView.getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        ViewObservable.bindView(recyclerView, GitHub.client().hottestRepositories())
                .map(Response::entity)
                .subscribe(new ResponseSubscriber());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RepositoryItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Repository repository = repositories.get(position);
        ((RepositoryItemViewHolder) viewHolder).bind(repository);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    private void showError() {
        ToastHelper.showError(context);
    }

    private class ResponseSubscriber extends Subscriber<SearchResult<Repository>> {

        @Override
        public void onCompleted() {
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            showError();
        }

        @Override
        public void onNext(SearchResult<Repository> searchResult) {
            if (searchResult.getItems() == null || searchResult.getItems().isEmpty()) {
                return;
            }

            List<Repository> items = searchResult.getItems();
            int startPosition = repositories.size();
            repositories.addAll(items);
            notifyItemRangeInserted(startPosition, items.size());
        }
    }
}

