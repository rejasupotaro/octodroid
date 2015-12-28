package com.example.octodroid.views.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.example.octodroid.R
import com.example.octodroid.activities.LoginActivity
import com.rejasupotaro.octodroid.models.User
import com.squareup.picasso.Picasso

class ProfileView : FrameLayout {
    val signInButton: Button by bindView(R.id.sign_in_button)
    val container: ViewGroup by bindView(R.id.container)
    val userImageView: ImageView by bindView(R.id.user_image)
    val userNameTextView: TextView by bindView(R.id.user_name)

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup()
    }

    fun setup() {
        View.inflate(context, R.layout.view_profile, this)
        findViewById(R.id.sign_in_button).setOnClickListener {
            LoginActivity.launch(context)
        }
    }

    fun setUser(user: User?) {
        if (user != null) {
            signInButton.visibility = View.GONE
            container.visibility = View.VISIBLE

            Picasso.with(userImageView.context).load(user.avatarUrl).into(userImageView)
            userNameTextView.text = user.getLogin()
        } else {
            signInButton.visibility = View.VISIBLE
            container.visibility = View.GONE
        }
    }
}
