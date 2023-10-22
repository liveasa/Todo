package com.example.todo_new

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RestFulWebService will be a singleton class
object RestFulWebService {

    // initialize webservice
    private val mWebService: WebService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://415e-116-206-244-83.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("M/d/yyyy h:m:s a").create()))
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
            .build()
        retrofit.create(WebService::class.java)
    }

    // returns webservice implementation
    fun getWebService() = mWebService
}