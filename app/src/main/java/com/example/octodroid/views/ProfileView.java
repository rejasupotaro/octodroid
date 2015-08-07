package com.example.octodroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.octodroid.R;
import com.rejasupotaro.octodroid.models.User;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileView extends FrameLayout {
    @InjectView(R.id.user_image)
    ImageView userImageView;
    @InjectView(R.id.user_name)
    TextView userNameTextView;

    public ProfileView(Context context) {
        super(context);
    }

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setup() {
        View view = View.inflate(getContext(), R.layout.view_profile, null);
        addView(view);
        ButterKnife.inject(this);
    }

    public void setUser(User user) {
        Glide.with(userImageView.getContext())
                .load(user.getAvatarUrl())
                .into(userImageView);
        userNameTextView.setText(user.getLogin());
    }
}
