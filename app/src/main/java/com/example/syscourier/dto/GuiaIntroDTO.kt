/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para la introducción de una guía en el sistema de mensajería.
 *
 * @property id Identificador único de la guía.
 * @property remitenteNombre Nombre del remitente.
 * @property destinatarioDireccion Dirección del destinatario.
 * @constructor Crea un objeto GuiaIntroDTO con la información proporcionada.
 *
 * @author Nicolas Peña
 */
package com.example.syscourier.dto

class GuiaIntroDTO(
    val id: Int,
    val remitenteNombre: String,
    val destinatarioDireccion: String
)
