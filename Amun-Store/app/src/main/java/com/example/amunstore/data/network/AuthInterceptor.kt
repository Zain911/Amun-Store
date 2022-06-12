package com.example.amunstore.data.network

import okhttp3.Credentials.basic
import okhttp3.Interceptor

class AuthInterceptor : Interceptor {

    //TODO change the auth when you get the new username and password from the supervisor
    private val username = "54e7ce1d28a9d3b395830ea17be70ae1"
    private val password = "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"
    private var credentials: String = basic(username, password)

     override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}