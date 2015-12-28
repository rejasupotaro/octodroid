package com.example.octodroid.views.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.example.octodroid.R
import com.example.octodroid.activities.LoginActivity
import com.rejasupotaro.octodroid.models.User
import com.squareup.picasso.Picasso

import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick

class ProfileView : FrameLayout {
    @Bind(R.id.sign_in_button)
    internal var signInButton: Button
    @Bind(R.id.container)
    internal var container: ViewGroup
    @Bind(R.id.user_image)
    internal var userImageView: ImageView
    @Bind(R.id.user_name)
    internal var userNameTextView: TextView

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    fun setup() {
        View.inflate(getContext(), R.layout.view_profile, this)
        ButterKnife.bind(this)
    }

    fun setUser(user: User?) {
        if (user != null) {
            signInButton.setVisibility(View.GONE)
            container.setVisibility(View.VISIBLE)

            Picasso.with(userImageView.getContext()).load(user!!.getAvatarUrl()).into(userImageView)
            userNameTextView.setText(user!!.getLogin())
        } else {
            signInButton.setVisibility(View.VISIBLE)
            container.setVisibility(View.GONE)
        }
    }

    @OnClick(R.id.sign_in_button)
    internal fun onSignInButtonClick() {
        LoginActivity.launch(getContext())
    }
}
