package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.example.octodroid.R
import com.makeramen.RoundedImageView
import com.rejasupotaro.octodroid.models.Repository
import com.squareup.picasso.Picasso

class RepositoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameTextView: TextView by bindView(R.id.name)
    val descriptionTextView: TextView by bindView(R.id.description)
    val userImageView: RoundedImageView by bindView(R.id.user_image)
    val userNameTextView: TextView by bindView(R.id.user_name)

    fun bind(repository: Repository) {
        nameTextView.text = repository.name
        descriptionTextView.text = repository.description
        Picasso.with(userImageView.context).load(repository.owner.avatarUrl).into(userImageView)
        userNameTextView.text = repository.owner.login
    }

    companion object {
        fun create(parent: ViewGroup): RepositoryItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_repository, parent, false)
            return RepositoryItemViewHolder(view)
        }
    }
}

