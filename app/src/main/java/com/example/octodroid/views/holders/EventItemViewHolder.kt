package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.octodroid.R
import com.rejasupotaro.octodroid.models.Comment
import com.rejasupotaro.octodroid.models.Event
import com.rejasupotaro.octodroid.models.Issue
import com.rejasupotaro.octodroid.models.PullRequest
import com.rejasupotaro.octodroid.models.User
import com.squareup.phrase.Phrase
import com.squareup.picasso.Picasso

import butterknife.Bind
import butterknife.ButterKnife

class EventItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @Bind(R.id.user_image)
    internal var userImageView: ImageView
    @Bind(R.id.head_text)
    internal var headTextView: TextView
    @Bind(R.id.description_text)
    internal var descriptionTextView: TextView

    init {
        ButterKnife.bind(this, view)
    }

    fun bind(event: Event) {
        val user = event.getUser()
        Picasso.with(userImageView.getContext()).load(user.getAvatarUrl()).into(userImageView)
        descriptionTextView.setText("")

        when (event.getType()) {
            Event.Type.CREATE -> bindCreate(event)
            Event.Type.DELETE -> bindDelete(event)
            Event.Type.FORK -> bindFork(event)
            Event.Type.ISSUE_COMMENT -> bindIssueComment(event)
            Event.Type.ISSUES -> bindIssues(event)
            Event.Type.MEMBER -> bindMember(event)
            Event.Type.PULL_REQUEST -> bindPullRequest(event)
            Event.Type.PULL_REQUEST_REVIEW_COMMENT -> bindPullRequestComment(event)
            Event.Type.PUSH -> bindPush(event)
            Event.Type.WATCH -> bindWatch(event)
            else -> headTextView.setText(event.getType().toString())
        }

        if (descriptionTextView.getText().length == 0) {
            descriptionTextView.setVisibility(View.GONE)
        } else {
            descriptionTextView.setVisibility(View.VISIBLE)
        }
    }

    private fun bindCreate(event: Event) {
        val user = event.getUser()
        val text = Phrase.from(headTextView.getContext(), R.string.event_create).put("username", user.getLogin()).put("ref_type", event.getPayload().getRefType()).put("ref", event.getPayload().getRef()).format()
        headTextView.setText(text)
    }

    private fun bindDelete(event: Event) {
        val user = event.getUser()
        val text = Phrase.from(headTextView.getContext(), R.string.event_delete).put("username", user.getLogin()).put("ref_type", event.getPayload().getRefType()).put("ref", event.getPayload().getRef()).format()
        headTextView.setText(text)
    }

    private fun bindFork(event: Event) {
        val user = event.getUser()
        val text = Phrase.from(headTextView.getContext(), R.string.event_fork).put("username", user.getLogin()).format()
        headTextView.setText(text)
    }

    private fun bindIssueComment(event: Event) {
        val user = event.getUser()
        val issue = event.getPayload().getIssue()
        val text = Phrase.from(headTextView.getContext(), R.string.event_issue_comment).put("username", user.getLogin()).put("issue", issue.getTitle()).format()
        headTextView.setText(text)
        val comment = event.getPayload().getComment()
        descriptionTextView.setText(comment.getBody())
    }

    private fun bindIssues(event: Event) {
        val user = event.getUser()
        val issue = event.getPayload().getIssue()
        val text = Phrase.from(headTextView.getContext(), R.string.event_issues).put("username", user.getLogin()).put("number", issue.getNumber()).format()
        headTextView.setText(text)
        descriptionTextView.setText("%s\n%s".format(issue.getTitle(), issue.getBody()))
    }

    private fun bindMember(event: Event) {
        val user = event.getPayload().getMember()

        Picasso.with(userImageView.getContext()).load(user.getAvatarUrl()).into(userImageView)
        descriptionTextView.setText("")

        val text = Phrase.from(headTextView.getContext(), R.string.event_member).put("username", user.getLogin()).format()
        headTextView.setText(text)
    }

    private fun bindPullRequest(event: Event) {
        val user = event.getUser()
        val pullRequest = event.getPayload().getPullRequest()
        val text = Phrase.from(headTextView.getContext(), R.string.event_pull_request).put("username", user.getLogin()).put("number", pullRequest.getNumber()).format()
        headTextView.setText(text)
        descriptionTextView.setText("%s\n%s".format(pullRequest.getTitle(), pullRequest.getBody()))
    }

    private fun bindPullRequestComment(event: Event) {
        val user = event.getUser()
        val pullRequset = event.getPayload().getPullRequest()
        val text = Phrase.from(headTextView.getContext(), R.string.event_pull_request_comment).put("username", user.getLogin()).put("pull_request", pullRequset.getTitle()).format()
        headTextView.setText(text)
        val comment = event.getPayload().getComment()
        descriptionTextView.setText(comment.getBody())
    }

    private fun bindPush(event: Event) {
        val user = event.getUser()
        val ref = event.getPayload().getRef()
        val text = Phrase.from(headTextView.getContext(), R.string.event_push).put("username", user.getLogin()).put("ref", ref).format()
        headTextView.setText(text)
    }

    private fun bindWatch(event: Event) {
        val user = event.getUser()
        val text = Phrase.from(headTextView.getContext(), R.string.event_watch).put("username", user.getLogin()).format()
        headTextView.setText(text)
    }

    companion object {

        fun create(parent: ViewGroup): EventItemViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false)
            return EventItemViewHolder(view)
        }
    }
}

