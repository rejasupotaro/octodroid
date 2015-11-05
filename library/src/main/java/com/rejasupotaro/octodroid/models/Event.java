package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.Date;

public class Event extends Resource {
    @SerializedName("id")
    private long id;
    @SerializedName("type")
    private Type type;
    @SerializedName("public")
    private boolean isPublic;
    @SerializedName("payload")
    private Payload payload;
    @SerializedName("repo")
    private Repository repository;
    @SerializedName("actor")
    private User user;
    @SerializedName("org")
    private JSONObject organization;
    @SerializedName("created_at")
    private Date createdAt;

    public long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Payload getPayload() {
        return payload;
    }

    public Repository getRepository() {
        return repository;
    }

    public User getUser() {
        return user;
    }

    public JSONObject getOrganization() {
        return organization;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public enum Type {
        COMMIT_COMMENT("CommitCommentEvent"),
        CREATE("CreateEvent"),
        DELETE("DeleteEvent"),
        DEPLOYMENT("DeploymentEvent"),
        DEVELOPMENT_STATUS("DeploymentStatusEvent"),
        DOWNLOAD("DownloadEvent"),
        FOLLOW("FollowEvent"),
        FORK("ForkEvent"),
        FORK_APPLY("ForkApplyEvent"),
        GIST("GistEvent"),
        GOLLUM("GollumEvent"),
        ISSUE_COMMENT("IssueCommentEvent"),
        ISSUES("IssuesEvent"),
        MEMBER("MemberEvent"),
        MEMBERSHIP("MembershipEvent"),
        PAGE_BUILD("PageBuildEvent"),
        PUBLIC("PublicEvent"),
        PULL_REQUEST("PullRequestEvent"),
        PULL_REQUEST_REVIEW_COMMENT("PullRequestReviewCommentEvent"),
        PUSH("PushEvent"),
        RELEASE("ReleaseEvent"),
        REPOSITORY("RepositoryEvent"),
        STATUS("StatusEvent"),
        TEAM_ADD("TeamAddEvent"),
        WATCH("WatchEvent");

        private String src;

        Type(String src) {
            this.src = src;
        }

        public static Type of(String src) {
            for (Type type : values()) {
                if (type.src.equals(src)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Given type is not supported: " + src);
        }
    }
}
