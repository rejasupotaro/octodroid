package com.example.octodroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.octodroid.R;
import com.example.octodroid.activities.LoginActivity;
import com.rejasupotaro.octodroid.models.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProfileView extends FrameLayout {
    @InjectView(R.id.sign_in_button)
    Button signInButton;
    @InjectView(R.id.container)
    ViewGroup container;
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
        View.inflate(getContext(), R.layout.view_profile, this);
        ButterKnife.inject(this);
    }

    public void setUser(User user) {
        if (user != null) {
            signInButton.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);

            Glide.with(userImageView.getContext())
                    .load(user.getAvatarUrl())
                    .into(userImageView);
            userNameTextView.setText(user.getLogin());
        } else {
            signInButton.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.sign_in_button)
    void onSignInButtonClick() {
        LoginActivity.launch(getContext());
    }
}
