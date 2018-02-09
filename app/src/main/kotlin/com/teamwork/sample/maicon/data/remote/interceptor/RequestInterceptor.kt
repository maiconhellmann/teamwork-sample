package com.teamwork.sample.maicon.data.remote.interceptor

import com.teamwork.sample.maicon.BuildConfig
import com.teamwork.sample.maicon.data.remote.RemoteConstants
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val newRequest = request?.newBuilder()

        try {

            newRequest?.addHeader("Accept", "application/json")
            newRequest?.addHeader("Content-Type", "application/json")
            newRequest?.addHeader(RemoteConstants.AUTHORIZATION, Credentials.basic(BuildConfig.AUTHORIZATION_TOKEN, "x"))
        }catch (ex: Throwable){
            Timber.w(ex, "Error intercepting request chain")
        }

        return chain?.proceed(newRequest?.build())!!
    }
}