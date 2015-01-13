package com.example.octodroid;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.rejasupotaro.octodroid.models.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static class ViewType {
        private static final int ITEM = 1;
        private static final int FOOTER = 2;
    }

    private List<Repository> repositories = new ArrayList<>();

    public RepositoryAdapter(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()));
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
        if (position == repositories.size()) {
            return ViewType.FOOTER;
        } else {
            return ViewType.ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (repositories.size() == 0) {
            return 0;
        } else {
            return repositories.size() + 1;
        }
    }

    public void clear() {
        repositories.clear();
        notifyDataSetChanged();
    }

    public void addRepositories(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }
}