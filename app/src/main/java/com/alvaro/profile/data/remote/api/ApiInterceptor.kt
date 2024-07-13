package com.alvaro.profile.data.remote.api

import com.alvaro.profile.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        
        val requestBuilder = original.newBuilder()

        requestBuilder.addHeader("app-id", BuildConfig.APP_ID)

        return chain.proceed(requestBuilder.build())
    }
}