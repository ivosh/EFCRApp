package com.rheagroup.efcr.app.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenProvider: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        tokenProvider.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")

        }

        return chain.proceed(requestBuilder.build())
    }
}