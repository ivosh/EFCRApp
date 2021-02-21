package com.rheagroup.efcr.app.network

data class ResourceStatus(val kind: Kind, val message: String?) {
    enum class Kind {
        API_ERROR,
        AUTHENTICATION_ERROR,
        GENERIC_ERROR,
        LOADING,
        NETWORK_ERROR,
        SUCCESS,
    }

    companion object {
        fun apiError(message: String) = ResourceStatus(Kind.API_ERROR, message)
        fun authenticationError(message: String) = ResourceStatus(Kind.AUTHENTICATION_ERROR, message)
        fun genericError(message: String) = ResourceStatus(Kind.GENERIC_ERROR, message)
        fun loading() = ResourceStatus(Kind.LOADING, null)
        fun networkError(message: String) = ResourceStatus(Kind.NETWORK_ERROR, message)
        fun success() = ResourceStatus(Kind.SUCCESS, null)
    }

    fun isFinal(): Boolean {
        return when (this.kind) {
            Kind.API_ERROR, Kind.AUTHENTICATION_ERROR, Kind.GENERIC_ERROR, Kind.NETWORK_ERROR,
            Kind.SUCCESS -> true
            Kind.LOADING -> false
        }
    }

    fun isError(): Boolean {
        return when (this.kind) {
            Kind.API_ERROR, Kind.AUTHENTICATION_ERROR, Kind.GENERIC_ERROR, Kind.NETWORK_ERROR -> true
            Kind.LOADING, Kind.SUCCESS -> false
        }
    }
}
