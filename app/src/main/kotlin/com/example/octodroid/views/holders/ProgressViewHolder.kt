package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.octodroid.R

class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun create(parent: ViewGroup): ProgressViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_progress, parent, false)
            return ProgressViewHolder(view)
        }
    }
}
