/**
 * Interfaz que define los endpoints y métodos para realizar llamadas a la API del sistema de mensajería.
 *
 * Esta interfaz utiliza Retrofit para definir las solicitudes HTTP a la API.
 */
package com.example.syscourier.API

import com.example.syscourier.dto.TokenDTO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    /**
     * Realiza una solicitud POST al endpoint "loginMensajero" para obtener los datos del usuario.
     *
     * @param correo El correo electrónico del usuario.
     * @param contrasena La contraseña del usuario.
     * @return Un objeto [Call] que representa la solicitud asíncrona con el token del usuario.
     */
    @FormUrlEncoded
    @POST("loginMensajero")
    fun obtenerDatos(
        @Field("correo") correo: String,
        @Field("password") contrasena: String
    ): Call<TokenDTO>
}
