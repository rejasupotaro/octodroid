# octodroid

GitHub API Client for Android.

# How to use

```java
GitHub.client().myself()
        .map(r -> r.entity())
        .subscribe(user -> ...);

GitHub.client().search("Android", "stars", "desc", 1, 20)
        .map(r -> r.entity())
        .subscribe(repositories -> ...);
```
