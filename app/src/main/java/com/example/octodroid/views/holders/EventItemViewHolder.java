package com.example.octodroid.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.octodroid.R;
import com.rejasupotaro.octodroid.models.Comment;
import com.rejasupotaro.octodroid.models.Event;
import com.rejasupotaro.octodroid.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.user_image)
    ImageView userImageView;
    @Bind(R.id.user_name_text)
    TextView userNameText;
    @Bind(R.id.body_text)
    TextView bodyTextView;

    public static EventItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new EventItemViewHolder(view);
    }

    public EventItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(final Event event) {
        User user = event.getUser();

        switch (event.getType()) {
            case IssueComment:
                Comment comment = event.getPayload().getComment();

                Glide.with(userImageView.getContext())
                        .load(user.getAvatarUrl())
                        .into(userImageView);

                userNameText.setText(user.getLogin());

                bodyTextView.setText(comment.getBody());
                break;
            default:
                Glide.with(userImageView.getContext())
                        .load(user.getAvatarUrl())
                        .into(userImageView);

                bodyTextView.setText(event.getType().toString());
                break;
        }
    }
}

