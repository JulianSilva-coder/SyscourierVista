package com.example.syscourier

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object ApiClient {
    private const val BASE_URL = "http://3.141.97.240:80/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())) // configuración lenient para Gson
        .build()
}
