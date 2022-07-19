package com.fromto.retrofit

import com.fromto.config.Application.Companion.X_ACCESS_TOKEN
import com.fromto.config.Application.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = prefs.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("x-access-token", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}