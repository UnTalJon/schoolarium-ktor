package com.schoolarium.data

sealed class ServiceResult<T> {
    data class Success<T>(val data: T) : ServiceResult<T>()
    data class NotFound<T>(val message: String) : ServiceResult<T>()
    data class Error<T>(val message: String, val exception: Throwable? = null) : ServiceResult<T>()
}