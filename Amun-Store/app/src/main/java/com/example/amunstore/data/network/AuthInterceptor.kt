package com.example.amunstore.data.network

import okhttp3.Credentials.basic
import okhttp3.Interceptor

class AuthInterceptor : Interceptor {

    //TODO change the auth when you get the new username and password from the supervisor
    private val username = "c48655414af1ada2cd256a6b5ee391be"
    private val password = "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"
    private var credentials: String = basic(username, password)

     override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}