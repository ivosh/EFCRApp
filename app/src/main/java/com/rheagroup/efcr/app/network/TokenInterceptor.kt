package com.rheagroup.efcr.app.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenProvider: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if ((request.url.encodedPath == "/api/v1/auth/token") && (request.method == "POST")) {
            return chain.proceed(request)
        }

        val requestBuilder = request.newBuilder()
        tokenProvider.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}
