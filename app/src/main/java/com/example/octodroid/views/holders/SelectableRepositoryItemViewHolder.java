package com.example.octodroid.views.holders;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.octodroid.R;
import com.rejasupotaro.octodroid.models.Repository;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectableRepositoryItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.repository_name_text)
    TextView repositoryNameTextView;
    @Bind(R.id.description)
    TextView descriptionTextView;
    @Bind(R.id.checkbox)
    CheckBox checkBox;

    public static SelectableRepositoryItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selectable_repository, parent, false);
        return new SelectableRepositoryItemViewHolder(view);
    }

    public SelectableRepositoryItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(final Repository repository, boolean isSelected, OnSelectRepositoryListener listener) {
        repositoryNameTextView.setText(String.format("%s / %s",
                repository.getOwner().getLogin(),
                repository.getName()));

        if (TextUtils.isEmpty(repository.getDescription())) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(repository.getDescription());
            descriptionTextView.setVisibility(View.VISIBLE);
        }

        checkBox.setOnCheckedChangeListener((v, isChecked) -> {
            if (isChecked) {
                listener.onSelect(repository.getId());
            } else {
                listener.onUnSelect(repository.getId());
            }
        });
        checkBox.setChecked(isSelected);
    }

    public interface OnSelectRepositoryListener {
        void onSelect(int id);

        void onUnSelect(int id);
    }
}

