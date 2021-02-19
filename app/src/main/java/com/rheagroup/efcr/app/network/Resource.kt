package com.rheagroup.efcr.app.network

/**
 * A generic class that holds a value with its loading status.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }

    override fun toString(): String {
        return when (this.status) {
            Status.SUCCESS -> "Success[data=$data]"
            Status.ERROR -> "Error[message=$message, data=$data]"
            Status.LOADING -> "Loading[data=$data]"
        }
    }
}
