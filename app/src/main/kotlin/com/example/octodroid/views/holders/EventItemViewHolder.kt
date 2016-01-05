package com.example.octodroid.views.holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.example.octodroid.R
import com.rejasupotaro.octodroid.models.Event
import com.squareup.phrase.Phrase
import com.squareup.picasso.Picasso

class EventItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userImageView: ImageView by bindView(R.id.user_image)
    val headTextView: TextView by bindView(R.id.head_text)
    val descriptionTextView: TextView by bindView(R.id.description_text)

    fun bind(event: Event) {
        val user = event.user
        Picasso.with(userImageView.context).load(user.avatarUrl).into(userImageView)
        descriptionTextView.text = ""

        when (event.type) {
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
            else -> headTextView.text = event.type.toString()
        }

        if (descriptionTextView.text.length == 0) {
            descriptionTextView.visibility = View.GONE
        } else {
            descriptionTextView.visibility = View.VISIBLE
        }
    }

    private fun bindCreate(event: Event) {
        val user = event.user
        val text = Phrase.from(headTextView.context, R.string.event_create).put("username", user.login).put("ref_type", event.payload.refType).put("ref", event.payload.ref).format()
        headTextView.text = text
    }

    private fun bindDelete(event: Event) {
        val user = event.user
        val text = Phrase.from(headTextView.context, R.string.event_delete).put("username", user.getLogin()).put("ref_type", event.payload.refType).put("ref", event.payload.ref).format()
        headTextView.text = text
    }

    private fun bindFork(event: Event) {
        val user = event.user
        val text = Phrase.from(headTextView.context, R.string.event_fork).put("username", user.login).format()
        headTextView.text = text
    }

    private fun bindIssueComment(event: Event) {
        val user = event.user
        val issue = event.payload.issue
        val text = Phrase.from(headTextView.context, R.string.event_issue_comment).put("username", user.login).put("issue", issue.title).format()
        headTextView.text = text
        val comment = event.payload.comment
        descriptionTextView.text = comment.body
    }

    private fun bindIssues(event: Event) {
        val user = event.user
        val issue = event.payload.issue
        val text = Phrase.from(headTextView.context, R.string.event_issues).put("username", user.login).put("number", issue.number).format()
        headTextView.text = text
        descriptionTextView.text = "%s\n%s".format(issue.title, issue.body)
    }

    private fun bindMember(event: Event) {
        val user = event.payload.member

        Picasso.with(userImageView.context).load(user.avatarUrl).into(userImageView)
        descriptionTextView.text = ""

        val text = Phrase.from(headTextView.context, R.string.event_member).put("username", user.login).format()
        headTextView.text = text
    }

    private fun bindPullRequest(event: Event) {
        val user = event.user
        val pullRequest = event.payload.pullRequest
        val text = Phrase.from(headTextView.context, R.string.event_pull_request).put("username", user.login).put("number", pullRequest.number).format()
        headTextView.text = text
        descriptionTextView.text = "%s\n%s".format(pullRequest.title, pullRequest.body)
    }

    private fun bindPullRequestComment(event: Event) {
        val user = event.user
        val pullRequset = event.payload.pullRequest
        val text = Phrase.from(headTextView.context, R.string.event_pull_request_comment).put("username", user.login).put("pull_request", pullRequset.title).format()
        headTextView.text = text
        val comment = event.payload.comment
        descriptionTextView.text = comment.body
    }

    private fun bindPush(event: Event) {
        val user = event.user
        val ref = event.payload.ref
        val text = Phrase.from(headTextView.context, R.string.event_push).put("username", user.login).put("ref", ref).format()
        headTextView.text = text
    }

    private fun bindWatch(event: Event) {
        val user = event.user
        val text = Phrase.from(headTextView.context, R.string.event_watch).put("username", user.login).format()
        headTextView.text = text
    }

    companion object {

        fun create(parent: ViewGroup): EventItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_event, parent, false)
            return EventItemViewHolder(view)
        }
    }
}

