# Trending Repositories on GitHub

An android app that lists trending Github repositories.

## Features

The android app lets you:
- Users can view the most trending repositories in Android from GitHub.
- Users can search the trending repository
- Offline Support

## Screenshots

[<img src="https://user-images.githubusercontent.com/43132866/173200093-60f4f8e9-1555-4e91-9f17-297a87edb510.png" align="left"
width="200"
hspace="4" vspace="4">](ss1.png)
[<img src="https://user-images.githubusercontent.com/43132866/173200104-f3040d42-ca7d-4175-bd0e-74a732eb0511.png" align="center"
width="200"
hspace="4" vspace="4">](ss2.png)
[<img src="https://user-images.githubusercontent.com/43132866/173200117-4a08db90-08db-45a9-a3e4-102ae1977366.png" align="left"
width="200"
hspace="4" vspace="4">](ss3.png)
[<img src="https://user-images.githubusercontent.com/43132866/173200127-7394b344-e67c-458d-9bf1-184d420cbf1e.png" align="center"
width="200"
hspace="4" vspace="4">](ss4.png)

## API
Since there is no official API for Trending Repositories (it is one of the internal GitHub API’s), using [GitHub Trending API](https://github-trending-api-wonder.herokuapp.com/)

## Tech stack
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Dagger-Hilt for dependency injection.
- JetPack
    - LiveData - notify domain layer data to views.
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - View Binding - bind UI elements to data.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Repository pattern
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
