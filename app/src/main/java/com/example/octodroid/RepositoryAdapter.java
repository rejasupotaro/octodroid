package com.example.octodroid;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rejasupotaro.octodroid.models.Repository;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Repository> repositories = new ArrayList<>();

    public RepositoryAdapter(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RepositoryItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Repository repository = repositories.get(i);
        ((RepositoryItemViewHolder) viewHolder).bind(repository);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void clear() {
        repositories.clear();
        notifyDataSetChanged();
    }

    public void addRepositories(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    public static class RepositoryItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.name)
        TextView nameTextView;

        public static RepositoryItemViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repository, parent, false);
            return new RepositoryItemViewHolder(view);
        }

        public RepositoryItemViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        private void bind(final Repository repository) {
            nameTextView.setText(repository.getName());
        }
    }
}