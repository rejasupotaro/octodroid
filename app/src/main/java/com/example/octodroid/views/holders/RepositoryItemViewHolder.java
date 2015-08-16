package com.example.octodroid.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.octodroid.R;
import com.makeramen.RoundedImageView;
import com.rejasupotaro.octodroid.models.Repository;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoryItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.name)
    TextView nameTextView;
    @Bind(R.id.description)
    TextView descriptionTextView;
    @Bind(R.id.user_image)
    RoundedImageView userImageView;
    @Bind(R.id.user_name)
    TextView userNameTextView;

    public static RepositoryItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repository, parent, false);
        return new RepositoryItemViewHolder(view);
    }

    public RepositoryItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(final Repository repository) {
        nameTextView.setText(repository.getName());
        descriptionTextView.setText(repository.getDescription());
        Glide.with(userImageView.getContext())
                .load(repository.getOwner().getAvatarUrl())
                .into(userImageView);
        userNameTextView.setText(repository.getOwner().getLogin());
    }
}

