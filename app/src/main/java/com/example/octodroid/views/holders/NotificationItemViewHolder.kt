package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.octodroid.R
import com.rejasupotaro.octodroid.models.Notification

import butterknife.Bind
import butterknife.ButterKnife

class NotificationItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @Bind(R.id.body)
    internal var bodyTextView: TextView

    init {
        ButterKnife.bind(this, view)
    }

    fun bind(notification: Notification) {
        bodyTextView.setText(notification.toJson())
    }

    companion object {

        fun create(parent: ViewGroup): NotificationItemViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false)
            return NotificationItemViewHolder(view)
        }
    }
}

