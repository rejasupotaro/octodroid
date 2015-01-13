package com.example.octodroid.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.octodroid.R;

public class ProgressViewHolder extends RecyclerView.ViewHolder {
    public static ProgressViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_progress, parent, false);
        return new ProgressViewHolder(view);
    }

    public ProgressViewHolder(View view) {
        super(view);
    }
}
