# octodroid

GitHub API Client for Android.

# How to use

```java
GitHub.client().user("rejasupotaro")
        .map(r -> r.entity())
        .subscribe(user -> ...);

GitHub.client().searchRepositories("Android", "stars", "desc", 1, 20)
        .map(r -> r.entity())
        .subscribe(repositories -> ...);

GitHub.client().hottestRepositories()
        .map(r -> r.entity())
        .subscribe(repositories -> ...);
```

### User basic authentication

```java
GitHub.client().authorize("username", "password");

GitHub.client().user()
        .map(r -> r.entity())
        .subscribe(user -> ...);
```

### Enable cache

```
GitHub.client().cache(this)
```
