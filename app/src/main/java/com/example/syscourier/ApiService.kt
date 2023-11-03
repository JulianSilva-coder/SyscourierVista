package com.example.syscourier

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("guiaintro/{id}")
    fun obtenerDatos(@Path("id") id: Int): Call<GuiaIntro> // ObjetoResultado es la clase que defines para representar la respuesta del servicio
}