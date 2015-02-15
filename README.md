# Octodroid

Android toolkit for the [GitHub API](https://developer.github.com/v3) using RxJava and RxAndroid.

## Getting Started

Let's start with a tangible example of how this library works.

```java
GitHub.client().searchRepositories("Android")
        .map(Response::entity)
        .map(SearchResult::getItems)
        .subscribe(repositories -> ...);
```

As you can see, we lose the callbacks. Fundamentally, RxJava is the Observer pattern, with for manipulating and transforming the stream of data our Observables emit. The Observable emits events, while the Observer subscribes and receives them. In the example above, The `.subscribe()` call adds an Observer to the Observable and the requests are made.

You should unsubscribe it when Activity is destroyed.

```java
@Override
public void onDestroy() {
    subscription.unsubscribe();
    super.onDestroy();
}
```

If you use ViewObservable of RxAndroid, you don't need to call `unsubscribe()` explicitly. ViewObservable calls `unsubscribe()` automatically when view is detached.

```java
ViewObservable.bindView(view, GitHub.client().user())
        .subscribe(user -> ...);
```

## APIs

```java
GitHub.client().user("rejasupotaro")
GitHub.client().search()
GitHub.client().searchRepositories("Android")
GitHub.client().hottestRepositories()
GitHub.client().userRepos()

// You can access these API if you have authenticated
GitHub.client().user()
GitHub.client().notifications()
```

## Authentication

```java
// You can give access token OAuth2
GitHub.client().authorize("access_token");
// or use basic authentication
GitHub.client().authorize("username", "password");

GitHub.client().user()
        .map(Response::entity)
        .subscribe(user -> ...);
```

## Set User-Agent

```java
GitHub.client().userAgent(userAgent);
```

## Enable Cache-Control

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


## Download

Download the latest JAR or grab via gradle:

```groovy
compile 'com.rejasupotaro:octodroid:0.1.2'
```
