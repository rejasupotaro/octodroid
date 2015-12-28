package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.example.octodroid.R
import com.rejasupotaro.octodroid.models.Notification

class NotificationItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bodyTextView: TextView by bindView(R.id.body)

    fun bind(notification: Notification) {
        bodyTextView.text = notification.toJson()
    }

    companion object {

        fun create(parent: ViewGroup): NotificationItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_notification, parent, false)
            return NotificationItemViewHolder(view)
        }
    }
}

