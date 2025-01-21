package com.walmart.core.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryRetrofitFactory {
    fun buildService(): CountryAPI {
        val gson = GsonBuilder().create()

        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return builder.create(CountryAPI::class.java)
    }
}