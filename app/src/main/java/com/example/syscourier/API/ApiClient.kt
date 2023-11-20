/**
 * Objeto singleton para manejar la configuración y creación del cliente de la API.
 *
 * Esta clase utiliza Retrofit para establecer la conexión con la API de la aplicación.
 *
 * Autor: Julian Silva
 */
package com.example.syscourier.API

import com.example.syscourier.MiApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object ApiClient {
    // URL base para la API, obtenida de la constante BASE_URL en MiApp
    private val BASE_URL = MiApp.BASE_URL

    // Objeto Retrofit para realizar llamadas a la API
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())) // Configuración lenient para Gson
        .build()
}
