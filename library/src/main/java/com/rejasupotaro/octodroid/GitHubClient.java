package com.rejasupotaro.octodroid;

import com.google.gson.reflect.TypeToken;
import com.rejasupotaro.octodroid.http.ApiClient;
import com.rejasupotaro.octodroid.http.Method;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.http.params.All;
import com.rejasupotaro.octodroid.http.params.Order;
import com.rejasupotaro.octodroid.http.params.Participating;
import com.rejasupotaro.octodroid.http.params.Sort;
import com.rejasupotaro.octodroid.http.params.Type;
import com.rejasupotaro.octodroid.models.Notification;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.SearchResult;
import com.rejasupotaro.octodroid.models.User;
import com.rejasupotaro.octodroid.utils.UrlUtils;

import org.joda.time.DateTime;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class GitHubClient {
    private static final int PER_PAGE = 20;

    private ApiClient apiClient;

    public GitHubClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void authorization(String username, String password) {
        apiClient.authorization(username, password);
    }

    public void authorization(String accessToken) {
        apiClient.authorization(accessToken);
    }

    @AuthenticationRequired
    public Observable<Response<User>> user() {
        return apiClient.request(Method.GET, "/user").to(new TypeToken<User>() {
        });
    }

    public Observable<Response<User>> user(String username) {
        String path = String.format("/users/%s", username);
        return apiClient.request(Method.GET, path).to(new TypeToken<User>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<Notification>> notification(int id) {
        String path = String.format("/notifications/threads/%d", id);
        return apiClient.request(Method.GET, path).to(new TypeToken<Notification>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<List<Notification>>> notifications() {
        return notifications(All.FALSE, Participating.FALSE);
    }

    @AuthenticationRequired
    public Observable<Response<List<Notification>>> notifications(All all, Participating participating) {
        String path = String.format("/notifications?all=%s&participating=%s",
                all.toString(), participating.toString());
        return apiClient.request(Method.GET, path).to(new TypeToken<List<Notification>>() {
        });
    }

    public Observable<Response<List<Notification>>> reposNotifications(String owner, String repo) {
        return reposNotifications(owner, repo, All.FALSE, Participating.FALSE);
    }

    public Observable<Response<List<Notification>>> reposNotifications(String owner, String repo, All all, Participating participating) {
        String path = String.format("/repos/%s/%s/notifications?all=%s&participating=%s",
                owner, repo, all.toString(), participating.toString());
        return apiClient.request(Method.GET, path).to(new TypeToken<List<Notification>>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<Void>> markAsRead() {
        String path = "/notifications";
        return apiClient.request(Method.PUT, path).to(new TypeToken<Void>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<Void>> markNotificationsAsReadInRepository(String owner, String repo) {
        String path = String.format("/repos/%s/%s/notifications",
                owner, repo);
        return apiClient.request(Method.PUT, path).to(new TypeToken<Void>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<Void>> markThreadAsRead(int id) {
        String path = String.format("/notifications/threads/%d",
                id);
        return apiClient.request(Method.PATCH, path).to(new TypeToken<Void>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<List<Repository>>> userRepos() {
        return userRepos(Type.ALL, Sort.FULL_NAME, Order.DESC);
    }

    @AuthenticationRequired
    public Observable<Response<List<Repository>>> userRepos(Type type, Sort sort, Order order) {
        String path = String.format("/user/repos?type=%s&sort=%s&direction=%s",
                type.toString(), sort.toString(), order.toString());
        return apiClient.request(Method.GET, path).to(new TypeToken<List<Repository>>() {
        });
    }

    public Observable<Response<SearchResult<Repository>>> searchRepositories(final String q) {
        return searchRepositories(q, null, null);
    }

    public Observable<Response<SearchResult<Repository>>> searchRepositories(final String q, final Sort sort, final Order order) {
        return searchRepositories(q, sort, order, 1);
    }

    public Observable<Response<SearchResult<Repository>>> searchRepositories(final String q, final Sort sort, final Order order, final int page) {
        StringBuilder builder = new StringBuilder();
        builder.append("/search/repositories?q=").append(UrlUtils.encode(q));
        builder.append("&page=").append(page);
        builder.append("&per_page").append(PER_PAGE);
        if (sort != null) {
            builder.append("&sort=").append(sort.toString());
        }
        if (order != null) {
            builder.append("&order=").append(order.toString());
        }

        String path = builder.toString();
        return apiClient.request(Method.GET, path).to(new TypeToken<SearchResult<Repository>>() {
        }).map(new Func1<Response<SearchResult<Repository>>, Response<SearchResult<Repository>>>() {
            @Override
            public Response<SearchResult<Repository>> call(Response<SearchResult<Repository>> r) {
                r.next(searchRepositories(q, sort, order, page + 1));
                return r;
            }
        });
    }

    public Observable<Response<SearchResult<User>>> searchUsers(final String q) {
        return searchUsers(q, null, null);
    }

    public Observable<Response<SearchResult<User>>> searchUsers(final String q, final Sort sort, final Order order) {
        return searchUsers(q, sort, order, 1);
    }

    public Observable<Response<SearchResult<User>>> searchUsers(final String q, final Sort sort, final Order order, final int page) {
        StringBuilder builder = new StringBuilder();
        builder.append("/search/users?q=").append(UrlUtils.encode(q));
        builder.append("&page=").append(page);
        builder.append("&per_page").append(PER_PAGE);
        if (sort != null) {
            builder.append("&sort=").append(sort.toString());
        }
        if (order != null) {
            builder.append("&order=").append(order.toString());
        }

        String path = builder.toString();
        return apiClient.request(Method.GET, path).to(new TypeToken<SearchResult<User>>() {
        }).map(new Func1<Response<SearchResult<User>>, Response<SearchResult<User>>>() {
            @Override
            public Response<SearchResult<User>> call(Response<SearchResult<User>> r) {
                r.next(searchUsers(q, sort, order, page + 1));
                return r;
            }
        });
    }

    // Find the hottest repositories created in the last week
    // `date -v-7d '+%Y-%m-%d'`
    public Observable<Response<SearchResult<Repository>>> hottestRepositories() {
        DateTime dateTime = new DateTime();
        String date = dateTime.minusDays(7).toString("yyyy-MM-dd");

        String path = String.format("/search/repositories?q=%s&sort=%s&order=%s&page=%d&per_page=%d",
                UrlUtils.encode("created:>" + date), "stars", "desc", 1, 10);
        return apiClient.request(Method.GET, path).to(new TypeToken<SearchResult<Repository>>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<List<Repository>>> starredRepositories() {
        String path = "/user/starred";
        return apiClient.request(Method.GET, path).to(new TypeToken<List<Repository>>() {
        });
    }

    @AuthenticationRequired
    public Observable<Response<List<Repository>>> starredRepositories(String username) {
        String path = String.format("/users/%s/starred", username);
        return apiClient.request(Method.GET, path).to(new TypeToken<List<Repository>>() {
        });
    }
}

