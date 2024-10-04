package com.dinhthi2004.restaurantmanager.common

sealed class Resources<T> (val data: T? = null, val message: String? = null, val errorDetails: String? = null) {
    class Success<T>(data: T?) : Resources<T>(data)
    class Error<T>(message: String, data: T? = null, errorDetails: String? = null) : Resources<T>(data, message, errorDetails)
    class Loading<T>(val isLoading: Boolean = true) : Resources<T>(null)
}