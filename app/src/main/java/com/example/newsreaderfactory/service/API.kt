package com.example.newsreaderfactory.service

import com.example.newsreaderfactory.MVP.model.ArticleList
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface API {
    @GET("v1/articles")
    fun getNews(
        @Query("source") source: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
    ): Call<ArticleList>

    companion object{
        fun createAPI(): API{

            val icp = HttpLoggingInterceptor()
            icp.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(icp)
                .addNetworkInterceptor(StethoInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(API::class.java)
        }
    }
}