package com.orangeink.trending.util

/**
 * A generic class that holds a value with its loading status.
 *
 * Resource is usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
sealed class Resource<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Resource<T>()
    data class Error(val exception: String) : Resource<Nothing>()
}