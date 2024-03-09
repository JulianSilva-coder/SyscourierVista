package com.example.syscourier
/**
 * Clase que contiene variables estáticas para almacenar información global de la aplicación.
 * Permite acceder y modificar el token de acceso y la URL base de la API.
 */
class MiApp {
    companion object {
        var accessToken: String = ""
        var HOST_NAME =  "54.224.39.170"
        var BASE_URL: String = "http://${HOST_NAME}/"
    }
}