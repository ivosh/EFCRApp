package com.rheagroup.efcr.app.network

import retrofit2.HttpException
import java.io.IOException

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
