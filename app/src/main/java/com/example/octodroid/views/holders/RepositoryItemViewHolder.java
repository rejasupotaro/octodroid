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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepositoryItemViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.name)
    TextView nameTextView;
    @InjectView(R.id.description)
    TextView descriptionTextView;
    @InjectView(R.id.user_image)
    RoundedImageView userImageView;
    @InjectView(R.id.user_name)
    TextView userNameTextView;

    public static RepositoryItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repository, parent, false);
        return new RepositoryItemViewHolder(view);
    }

    public RepositoryItemViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
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

