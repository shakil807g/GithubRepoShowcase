package com.shakil.githubreposhowcase.domain

enum class DataStatus {
    SUCCESS,
    ERROR,
    LOADING,
    IDLE
}

data class Resource<out T>(
    val status: DataStatus,
    val data: T?,
    val message: String?,
    val error: Throwable? = null
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(DataStatus.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable): Resource<T> {
            return Resource(DataStatus.ERROR, null, null, error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(DataStatus.LOADING, null, null)
        }

        fun <T> idle(): Resource<T> {
            return Resource(DataStatus.IDLE, null, null)
        }
    }
}