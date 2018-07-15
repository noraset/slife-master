package com.singhaestate.slife.manager.remote.service

import com.singhaestate.slife.manager.local.UserManager
import com.singhaestate.slife.util.GsonHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ServiceManager {

    companion object {

        private const val BASE_URL = "http://203.151.50.133:3002/"

        private const val HTTP_READ_TIMEOUT = 10_000L
        private const val HTTP_CONNECT_TIMEOUT = 6_000L
        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        const val CLIENT_ID = "qcGIABnrPh35DiwGSJXbt7EqB7QNZuFc"
        const val CLIENT_SECRET = "xHBbaRD3PLBtVuyBQE3Ad2SgWiQtIBsq"
        const val USER_SCOPE = "use-api"

        val service: ApiService by lazy {

            val okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val request = original.newBuilder()
                                .method(original.method(), original.body())
                                .build()

                        return@addInterceptor chain.proceed(request)
                    }
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonHelper.getGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

            retrofit.create(ApiService::class.java)
        }

        val serviceWithHeader: ApiService by lazy {

            val authorization = UserManager.user?.token?.token_type + " " + UserManager.user?.token?.access_token
            val okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val request = original.newBuilder()
                                .addHeader("Authorization", authorization)
                                .method(original.method(), original.body())
                                .build()

                        return@addInterceptor chain.proceed(request)
                    }
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonHelper.getGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

            retrofit.create(ApiService::class.java)

        }
    }
}