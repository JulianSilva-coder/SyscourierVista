package com.example.syscourier.dto

data class GuiaInfoDTO(
    val id: Int,
    val producto: String,
    val remitenteIdentificacion: String,
    val remitenteNombre: String,
    val remitenteDireccion: String,
    val remitenteTelefono: String,
    val destinatarioIdentificacion: String,
    val destinatarioNombre: String,
    val destinatarioDireccion: String,
    val destinatarioTelefono: String,
    val totalFlete: Double
)