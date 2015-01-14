package com.example.octodroid.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.octodroid.views.holders.RepositoryItemViewHolder;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.Repository;

import java.util.ArrayList;
import java.util.List;

import rx.android.view.ViewObservable;
import rx.subscriptions.CompositeSubscription;

public class HottestRepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Repository> repositories = new ArrayList<>();
    private CompositeSubscription subscription = new CompositeSubscription();

    public HottestRepositoryAdapter(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        subscription.add(ViewObservable.bindView(recyclerView, GitHub.client().hottestRepositories())
                .map(Response::entity)
                .subscribe(searchResult -> {
                    if (searchResult.getItems() == null || searchResult.getItems().isEmpty()) {
                        return;
                    }

                    List<Repository> repositories = searchResult.getItems();
                    addRepositories(repositories);
                }));
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

    private void addRepositories(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    public void destroy() {
        subscription.unsubscribe();
    }
}

