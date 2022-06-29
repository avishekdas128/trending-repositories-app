# Trending Repositories on GitHub

An android application that lists trending Github repositories.

## Features

The android app lets you:
- Users can view the most trending repositories in Android from GitHub.
- Users can search the trending repository
- Offline Support

## Screenshots

[<img src="https://user-images.githubusercontent.com/43132866/176552467-24f848bc-6c00-4f7a-aacb-c14816c453cb.png" align="left"
width="180"
hspace="1" vspace="1">](ss1.png)
[<img src="https://user-images.githubusercontent.com/43132866/176552774-2b9a5264-044b-4590-b6b5-82d9cb37bd0c.png" align="center"
width="180"
hspace="1" vspace="1">](ss2.png)
[<img src="https://user-images.githubusercontent.com/43132866/176552619-01425eae-eaab-4a19-8b2f-f5a593fb5d45.png" align="left"
width="180"
hspace="1" vspace="1">](ss3.png)
[<img src="https://user-images.githubusercontent.com/43132866/176553363-be8d9ce7-8451-4f5e-be01-22f5fbbdb358.png" align="left"
width="180"
hspace="1" vspace="1">](ss4.png)

## API
Since there is no official API for Trending Repositories (it is one of the internal GitHub API’s), using [GitHub Trending API](https://github-trending-api-wonder.herokuapp.com/)

## Tech stack
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Dagger-Hilt for dependency injection.
- JetPack
    - State - Notify domain layer data to views.
    - SharedFlows - Notify one time events to the UI layer.
    - Lifecycle - Dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - [Compose](https://developer.android.com/jetpack/compose) - Render UI.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Repository pattern
    - data ~ domain(use-cases) ~ presentation
- [Coil](https://coil-kt.github.io/coil/compose/) - Loading Images for Jetpack Compose.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs and Network data.
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room) - For offline caching of data
- Testing
    - [Turbine](https://github.com/cashapp/turbine) - For testing HotFlows
    - [Kotlin Coroutines Test](https://developer.android.com/kotlin/coroutines/test)
    - Mockito - Mocking Framework for writing unit tests.
    - [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - To mock the web server for APIs
