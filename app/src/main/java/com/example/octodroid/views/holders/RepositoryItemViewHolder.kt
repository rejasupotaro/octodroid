package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.octodroid.R
import com.makeramen.RoundedImageView
import com.rejasupotaro.octodroid.models.Repository
import com.squareup.picasso.Picasso

import butterknife.Bind
import butterknife.ButterKnife

class RepositoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @Bind(R.id.name)
    internal var nameTextView: TextView
    @Bind(R.id.description)
    internal var descriptionTextView: TextView
    @Bind(R.id.user_image)
    internal var userImageView: RoundedImageView
    @Bind(R.id.user_name)
    internal var userNameTextView: TextView

    init {
        ButterKnife.bind(this, view)
    }

    fun bind(repository: Repository) {
        nameTextView.setText(repository.getName())
        descriptionTextView.setText(repository.getDescription())
        Picasso.with(userImageView.getContext()).load(repository.getOwner().getAvatarUrl()).into(userImageView)
        userNameTextView.setText(repository.getOwner().getLogin())
    }

    companion object {

        fun create(parent: ViewGroup): RepositoryItemViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repository, parent, false)
            return RepositoryItemViewHolder(view)
        }
    }
}

