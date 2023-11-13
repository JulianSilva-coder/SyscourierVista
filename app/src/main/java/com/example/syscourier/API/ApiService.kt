package com.example.syscourier.API

import com.example.syscourier.TokenDTO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("loginMensajero")
    fun obtenerDatos(
        @Field("correo") correo: String,
        @Field("password") contrasena: String
    ): Call<TokenDTO>
}
