package com.rheagroup.efcr.app.network

import java.io.IOException
import retrofit2.HttpException

suspend fun <T> apiCall(apiCall: suspend () -> T): ApiResponse<T> {
    return try {
        ApiResponse.success(apiCall())
    } catch (exception: Exception) {
        when (exception) {
            is IOException -> ApiResponse.networkError(
                exception.message ?: "network problem"
            )
            is HttpException -> {
                if (exception.code() == 401) {
                    ApiResponse.authenticationError(
                        exception.message ?: "authentication problem"
                    )
                } else {
                    ApiResponse.apiError(exception.message ?: "API problem")
                }
            }
            else -> ApiResponse.genericError(exception.message ?: "generic error")
        }
    }
}
