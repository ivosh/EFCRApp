package com.rheagroup.efcr.app.network

data class ApiResponse<out T>(val kind: Kind, val data: T?, val message: String?) {
    enum class Kind {
        API_ERROR,
        AUTHENTICATION_ERROR,
        GENERIC_ERROR,
        NETWORK_ERROR,
        SUCCESS,
    }

    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(Kind.SUCCESS, data, null)
        }

        fun <T> apiError(message: String): ApiResponse<T> {
            return ApiResponse(Kind.API_ERROR, null, message)
        }

        fun <T> authenticationError(message: String): ApiResponse<T> {
            return ApiResponse(Kind.AUTHENTICATION_ERROR, null, message)
        }

        fun <T> genericError(message: String): ApiResponse<T> {
            return ApiResponse(Kind.GENERIC_ERROR, null, message)
        }

        fun <T> networkError(message: String): ApiResponse<T> {
            return ApiResponse(Kind.NETWORK_ERROR, null, message)
        }
    }

    fun toResourceStatus(): ResourceStatus {
        return when (this.kind) {
            Kind.SUCCESS -> ResourceStatus.success()
            Kind.API_ERROR -> ResourceStatus.apiError(this.message!!)
            Kind.GENERIC_ERROR -> ResourceStatus.genericError(this.message!!)
            Kind.AUTHENTICATION_ERROR -> ResourceStatus.authenticationError(this.message!!)
            Kind.NETWORK_ERROR -> ResourceStatus.networkError(this.message!!)
        }
    }
}
