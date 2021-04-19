package com.github.billman64.quickamazonbooks.model.network

import com.github.billman64.quickamazonbooks.model.data.AmazonBooksResponse
import com.github.billman64.quickamazonbooks.util.Constants.Companion.BASE_URL
import com.github.billman64.quickamazonbooks.util.Constants.Companion.END_POINT
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class AmazonRetrofitInstance {

    private val logging = HttpLoggingInterceptor().also {
        it.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val amazonService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create(AmazonService::class.java)

    fun getBooks(): Deferred<AmazonBooksResponse> = amazonService.getAmazonBooksAsync()

    interface AmazonService{
        @GET(END_POINT)
        fun getAmazonBooksAsync(): Deferred<AmazonBooksResponse>
    }
}