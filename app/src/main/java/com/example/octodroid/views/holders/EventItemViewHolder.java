package com.example.octodroid.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.octodroid.R;
import com.rejasupotaro.octodroid.models.Comment;
import com.rejasupotaro.octodroid.models.Event;
import com.rejasupotaro.octodroid.models.Issue;
import com.rejasupotaro.octodroid.models.PullRequest;
import com.rejasupotaro.octodroid.models.User;
import com.squareup.phrase.Phrase;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.user_image)
    ImageView userImageView;
    @Bind(R.id.head_text)
    TextView headTextView;
    @Bind(R.id.description_text)
    TextView descriptionTextView;

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
        Picasso.with(userImageView.getContext())
                .load(user.getAvatarUrl())
                .into(userImageView);
        descriptionTextView.setText("");

        switch (event.getType()) {
            case Create:
                bindCreate(event);
                break;
            case Fork:
                bindFork(event);
                break;
            case IssueComment:
                bindIssueComment(event);
                break;
            case Issues:
                bindIssues(event);
                break;
            case Member:
                bindMember(event);
                break;
            case PullRequest:
                bindPullRequest(event);
                break;
            case PullRequestReviewComment:
                bindPullRequestComment(event);
                break;
            case Push:
                bindPush(event);
                break;
            case Watch:
                bindWatch(event);
                break;
            default:
                headTextView.setText(event.getType().toString());
                break;
        }

        if (descriptionTextView.getText().length() == 0) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setVisibility(View.VISIBLE);
        }
    }

    private void bindCreate(Event event) {
        User user = event.getUser();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_create)
                .put("username", user.getLogin())
                .put("ref_type", event.getPayload().getRefType())
                .put("ref", event.getPayload().getRef())
                .format();
        headTextView.setText(text);
    }

    private void bindFork(Event event) {
        User user = event.getUser();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_fork)
                .put("username", user.getLogin())
                .format();
        headTextView.setText(text);
    }

    private void bindIssueComment(Event event) {
        User user = event.getUser();
        Issue issue = event.getPayload().getIssue();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_issue_comment)
                .put("username", user.getLogin())
                .put("issue", issue.getTitle())
                .format();
        headTextView.setText(text);
        Comment comment = event.getPayload().getComment();
        descriptionTextView.setText(comment.getBody());
    }

    private void bindIssues(Event event) {
        User user = event.getUser();
        Issue issue = event.getPayload().getIssue();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_issues)
                .put("username", user.getLogin())
                .put("number", issue.getNumber())
                .format();
        headTextView.setText(text);
        descriptionTextView.setText(String.format("%s\n%s", issue.getTitle(), issue.getBody()));
    }

    private void bindMember(Event event) {
        User user = event.getPayload().getMember();

        Picasso.with(userImageView.getContext())
                .load(user.getAvatarUrl())
                .into(userImageView);
        descriptionTextView.setText("");

        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_member)
                .put("username", user.getLogin())
                .format();
        headTextView.setText(text);
    }

    private void bindPullRequest(Event event) {
        User user = event.getUser();
        PullRequest pullRequest = event.getPayload().getPullRequest();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_pull_request)
                .put("username", user.getLogin())
                .put("number", pullRequest.getNumber())
                .format();
        headTextView.setText(text);
        descriptionTextView.setText(String.format("%s\n%s", pullRequest.getTitle(), pullRequest.getBody()));
    }

    private void bindPullRequestComment(Event event) {
        User user = event.getUser();
        PullRequest pullRequset = event.getPayload().getPullRequest();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_pull_request_comment)
                .put("username", user.getLogin())
                .put("pull_request", pullRequset.getTitle())
                .format();
        headTextView.setText(text);
        Comment comment = event.getPayload().getComment();
        descriptionTextView.setText(comment.getBody());
    }

    private void bindPush(Event event) {
        User user = event.getUser();
        String ref = event.getPayload().getRef();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_push)
                .put("username", user.getLogin())
                .put("ref", ref)
                .format();
        headTextView.setText(text);
    }

    private void bindWatch(Event event) {
        User user = event.getUser();
        CharSequence text = Phrase.from(headTextView.getContext(), R.string.event_watch)
                .put("username", user.getLogin())
                .format();
        headTextView.setText(text);
    }
}

