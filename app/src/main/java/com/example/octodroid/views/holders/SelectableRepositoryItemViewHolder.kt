package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

import com.example.octodroid.R
import com.rejasupotaro.octodroid.models.Repository

import butterknife.Bind
import butterknife.ButterKnife

class SelectableRepositoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @Bind(R.id.repository_name_text)
    internal var repositoryNameTextView: TextView
    @Bind(R.id.description)
    internal var descriptionTextView: TextView
    @Bind(R.id.checkbox)
    internal var checkBox: CheckBox

    init {
        ButterKnife.bind(this, view)
    }

    fun bind(repository: Repository, isSelected: Boolean, listener: OnSelectRepositoryListener) {
        repositoryNameTextView.setText("%s / %s".format(repository.getOwner().getLogin(), repository.getName()))

        if (TextUtils.isEmpty(repository.getDescription())) {
            descriptionTextView.setVisibility(View.GONE)
        } else {
            descriptionTextView.setText(repository.getDescription())
            descriptionTextView.setVisibility(View.VISIBLE)
        }

        checkBox.setOnCheckedChangeListener({ v, isChecked ->
            if (isChecked) {
                listener.onSelect(repository.getId())
            } else {
                listener.onUnSelect(repository.getId())
            }
        })
        checkBox.setChecked(isSelected)
    }

    interface OnSelectRepositoryListener {
        fun onSelect(id: Int)

        fun onUnSelect(id: Int)
    }

    companion object {

        fun create(parent: ViewGroup): SelectableRepositoryItemViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selectable_repository, parent, false)
            return SelectableRepositoryItemViewHolder(view)
        }
    }
}

