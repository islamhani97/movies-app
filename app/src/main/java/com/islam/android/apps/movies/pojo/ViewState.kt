package com.islam.android.apps.movies.pojo

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    class Success<out T>(val data: T?) : ViewState<T>()
    class Error(val message: String) : ViewState<Nothing>()
}