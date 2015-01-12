# Octodroid

Android toolkit for the [GitHub API](https://developer.github.com/v3).

## Quick start

I will upload to jcenter sooner or later.

## How to use

### Users

```java
GitHub.client().user("rejasupotaro")
        .map(r -> r.entity())
        .subscribe(user -> ...);

// You can access myself if you have authenticated
GitHub.client().user()
        .map(r -> r.entity())
        .subscribe(user -> ...);
```

### Repositories

```java
GitHub.client().searchRepositories("Android", "stars", "desc", 1, 20)
        .map(r -> r.entity())
        .subscribe(repositories -> ...);

GitHub.client().hottestRepositories()
        .map(r -> r.entity())
        .subscribe(repositories -> ...);
```

### Authentication

```java
// You can give access token OAuth2
GitHub.client().authorize("access_token");
// or use basic authentication
GitHub.client().authorize("username", "password");

GitHub.client().user()
        .map(r -> r.entity())
        .subscribe(user -> ...);
```

### Enable Cache-Control

```
GitHub.client().cache(this)
```

