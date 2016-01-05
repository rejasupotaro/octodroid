package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import butterknife.bindView
import com.example.octodroid.R
import com.rejasupotaro.octodroid.models.Repository

class SelectableRepositoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val repositoryNameTextView: TextView by bindView(R.id.repository_name_text)
    val descriptionTextView: TextView by bindView(R.id.description)
    val checkBox: CheckBox by bindView(R.id.checkbox)

    fun bind(repository: Repository, isSelected: Boolean, listener: OnSelectRepositoryListener) {
        repositoryNameTextView.text = "%s / %s".format(repository.owner.login, repository.name)

        if (TextUtils.isEmpty(repository.description)) {
            descriptionTextView.visibility = View.GONE
        } else {
            descriptionTextView.text = repository.description
            descriptionTextView.visibility = View.VISIBLE
        }

        checkBox.setOnCheckedChangeListener({ v, isChecked ->
            if (isChecked) {
                listener.onSelect(repository.id)
            } else {
                listener.onUnSelect(repository.id)
            }
        })
        checkBox.isChecked = isSelected
    }

    interface OnSelectRepositoryListener {
        fun onSelect(id: Int)

        fun onUnSelect(id: Int)
    }

    companion object {

        fun create(parent: ViewGroup): SelectableRepositoryItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_selectable_repository, parent, false)
            return SelectableRepositoryItemViewHolder(view)
        }
    }
}

