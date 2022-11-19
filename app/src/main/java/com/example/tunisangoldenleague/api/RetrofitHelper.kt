package com.example.tunisangoldenleague.api

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitHelper {
    val baseUrl = "http://tgl2.westeurope.cloudapp.azure.com/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}