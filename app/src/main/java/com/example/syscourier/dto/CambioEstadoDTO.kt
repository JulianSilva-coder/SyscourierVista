package com.example.syscourier.dto

data class CambioEstadoDTO(
    val guiaId: Int,
    val codEstado: Int,
    val motivo: String?,
    val observaciones: String
)