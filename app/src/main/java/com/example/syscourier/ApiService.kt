package com.example.syscourier

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
    ): Call<GuiaIntro>
}
