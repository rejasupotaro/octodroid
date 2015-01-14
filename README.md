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

### Set User-Agent

```java
GitHub.client().userAgent(userAgent);
```

### Enable Cache-Control

```java
CacheControl cacheControl = new CacheControl.Builder()
        // cache file is required
        .file(context)                 // use /data/data/package.name/cache/octodroid_response_cache when given context
//        .file(dir, "file_name")      // or give dir and file name
//        .file(file)                  // or give file
        .maxCacheSize(3 * 1024 * 1024) // default max cache size is 3MB
        .maxStale(28 * 24 * 60 * 60)   // default max stale is 3 weeks
        .build();

GitHub.client().cache(cacheControl);
```
