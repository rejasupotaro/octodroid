package com.rejasupotaro.octodroid;

import com.google.gson.reflect.TypeToken;
import com.rejasupotaro.octodroid.http.ApiClient;
import com.rejasupotaro.octodroid.http.Method;
import com.rejasupotaro.octodroid.http.Params;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.Event;
import com.rejasupotaro.octodroid.models.Notification;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.SearchResult;
import com.rejasupotaro.octodroid.models.User;

import org.joda.time.DateTime;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class GitHubClient {
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

    @RequireLogin
    public Observable<Response<User>> user() {
        return apiClient.request(Method.GET, "/user").to(new TypeToken<User>() {
        });
    }

    public Observable<Response<User>> user(String username) {
        String path = String.format("/users/%s", username);
        return apiClient.request(Method.GET, path)
                .to(new TypeToken<User>() {
                });
    }

    public Observable<Response<List<Event>>> repositoryEvents(String owner, String repo) {
        return repositoryEvents(owner, repo, new Params());
    }

    public Observable<Response<List<Event>>> repositoryEvents(final String owner, final String repo, final Params params) {
        String path = String.format("/repos/%s/%s/events", owner, repo);
        return apiClient.request(Method.GET, path)
                .to(new TypeToken<List<Event>>() {
                }).map(new Func1<Response<List<Event>>, Response<List<Event>>>() {
                    @Override
                    public Response<List<Event>> call(Response<List<Event>> r) {
                        if (r.hasNext()) {
                            params.incrementPage();
                            r.next(repositoryEvents(owner, repo, params));
                        }
                        return r;
                    }
                });
    }

    @RequireLogin
    public Observable<Response<Notification>> notification(int id) {
        String path = String.format("/notifications/threads/%d", id);
        return apiClient.request(Method.GET, path)
                .to(new TypeToken<Notification>() {
                });
    }

    @RequireLogin
    public Observable<Response<List<Notification>>> notifications() {
        return notifications(new Params());
    }

    @RequireLogin
    public Observable<Response<List<Notification>>> notifications(Params params) {
        return apiClient.request(Method.GET, "/notifications", params)
                .to(new TypeToken<List<Notification>>() {
                });
    }

    public Observable<Response<List<Notification>>> reposNotifications(String owner, String repo, Params params) {
        String path = String.format("/repos/%s/%s/notifications", owner, repo);
        return apiClient.request(Method.GET, path, params)
                .to(new TypeToken<List<Notification>>() {
                });
    }

    @RequireLogin
    public Observable<Response<Void>> markAsRead() {
        return apiClient.request(Method.PUT, "/notifications")
                .to(new TypeToken<Void>() {
                });
    }

    @RequireLogin
    public Observable<Response<Void>> markNotificationsAsReadInRepository(String owner, String repo) {
        String path = String.format("/repos/%s/%s/notifications", owner, repo);
        return apiClient.request(Method.PUT, path)
                .to(new TypeToken<Void>() {
                });
    }

    @RequireLogin
    public Observable<Response<Void>> markThreadAsRead(int id) {
        String path = String.format("/notifications/threads/%d", id);
        return apiClient.request(Method.PATCH, path)
                .to(new TypeToken<Void>() {
                });
    }

    @RequireLogin
    public Observable<Response<List<Repository>>> userRepos() {
        return userRepos(new Params());
    }

    @RequireLogin
    public Observable<Response<List<Repository>>> userRepos(final Params params) {
        return apiClient.request(Method.GET, "/user/repos", params)
                .to(new TypeToken<List<Repository>>() {
                }).map(new Func1<Response<List<Repository>>, Response<List<Repository>>>() {
                    @Override
                    public Response<List<Repository>> call(Response<List<Repository>> r) {
                        if (r.hasNext()) {
                            params.incrementPage();
                            r.next(userRepos(params));
                        }
                        return r;
                    }
                });
    }

    public Observable<Response<SearchResult<Repository>>> searchRepositories(String query) {
        Params params = new Params()
                .add("q", query);
        return searchRepositories(params);
    }

    public Observable<Response<SearchResult<Repository>>> searchRepositories(final Params params) {
        return apiClient.request(Method.GET, "/search/repositories", params)
                .to(new TypeToken<SearchResult<Repository>>() {
                }).map(new Func1<Response<SearchResult<Repository>>, Response<SearchResult<Repository>>>() {
                    @Override
                    public Response<SearchResult<Repository>> call(Response<SearchResult<Repository>> r) {
                        if (r.hasNext()) {
                            params.incrementPage();
                            r.next(searchRepositories(params));
                        }
                        return r;
                    }
                });
    }

    public Observable<Response<SearchResult<User>>> searchUsers(String username) {
        Params params = new Params()
                .add("q", username);
        return searchUsers(params);
    }

    public Observable<Response<SearchResult<User>>> searchUsers(final Params params) {
        return apiClient.request(Method.GET, "/search/users", params)
                .to(new TypeToken<SearchResult<User>>() {
                }).map(new Func1<Response<SearchResult<User>>, Response<SearchResult<User>>>() {
                    @Override
                    public Response<SearchResult<User>> call(Response<SearchResult<User>> r) {
                        if (r.hasNext()) {
                            params.incrementPage();
                            r.next(searchUsers(params));
                        }
                        return r;
                    }
                });
    }

    public Observable<Response<SearchResult<Repository>>> hottestRepositories() {
        DateTime dateTime = new DateTime();
        String date = dateTime.minusDays(7).toString("yyyy-MM-dd");

        Params params = new Params()
                .add("q", "created:>" + date)
                .add("sort", "stars")
                .add("order", "desc");

        return hottestRepositories(params);
    }

    // Find the hottest repositories created in the last week
    // `date -v-7d '+%Y-%m-%d'`
    public Observable<Response<SearchResult<Repository>>> hottestRepositories(final Params params) {
        return apiClient.request(Method.GET, "/search/repositories", params)
                .to(new TypeToken<SearchResult<Repository>>() {
                }).map(new Func1<Response<SearchResult<Repository>>, Response<SearchResult<Repository>>>() {
                    @Override
                    public Response<SearchResult<Repository>> call(Response<SearchResult<Repository>> r) {
                        if (r.hasNext()) {
                            params.incrementPage();
                            r.next(hottestRepositories(params));
                        }
                        return r;
                    }
                });
    }

    @RequireLogin
    public Observable<Response<List<Repository>>> starredRepositories() {
        return apiClient.request(Method.GET, "/user/starred")
                .to(new TypeToken<List<Repository>>() {
                });
    }

    @RequireLogin
    public Observable<Response<List<Repository>>> starredRepositories(String username) {
        String path = String.format("/users/%s/starred", username);
        return apiClient.request(Method.GET, path)
                .to(new TypeToken<List<Repository>>() {
                });
    }
}

