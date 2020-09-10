package com.kuyu.blockchain.http

import com.kuyu.blockchain.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * created by wangguoqun at 2020-09-09
 */
class ApiManager private constructor() {

    private fun newClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constant.BaseURL).client(newClient())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    companion object{
        val apiService = ApiManager().getRetrofit().create(ApiService::class.java)
    }

}
