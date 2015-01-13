package com.example.octodroid.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.octodroid.views.DividerItemDecoration;
import com.example.octodroid.views.holders.HottestRepositoryHeaderViewHolder;
import com.example.octodroid.views.holders.RepositoryItemViewHolder;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.models.Repository;

import java.util.ArrayList;
import java.util.List;

import rx.android.view.ViewObservable;
import rx.subscriptions.CompositeSubscription;

public class HottestRepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static class ViewType {
        private static final int ITEM = 1;
        private static final int HEADER = 2;
    }

    private List<Repository> repositories = new ArrayList<>();
    private CompositeSubscription subscription = new CompositeSubscription();

    public HottestRepositoryAdapter(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()));

        subscription.add(ViewObservable.bindView(recyclerView, GitHub.client().hottestRepositories())
                .map(r -> r.entity())
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
        if (viewType == ViewType.HEADER) {
            return HottestRepositoryHeaderViewHolder.create(parent);
        } else {
            return RepositoryItemViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == ViewType.HEADER) {
            // do nothing
        } else {
            Repository repository = repositories.get(position - 1);
            ((RepositoryItemViewHolder) viewHolder).bind(repository);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || repositories.size() == 0) {
            return ViewType.HEADER;
        } else {
            return ViewType.ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size() + 1;
    }

    private void addRepositories(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    public void destroy() {
        subscription.unsubscribe();
    }
}

