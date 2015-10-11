package com.example.octodroid.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.octodroid.R;
import com.rejasupotaro.octodroid.models.Notification;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.body)
    TextView bodyTextView;

    public static NotificationItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false);
        return new NotificationItemViewHolder(view);
    }

    public NotificationItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(final Notification notification) {
        bodyTextView.setText(notification.toJson());
    }
}

