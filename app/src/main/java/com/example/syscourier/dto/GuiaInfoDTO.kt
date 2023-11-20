/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para la información de una guía en el sistema de mensajería.
 *
 * @property id Identificador único de la guía.
 * @property producto Nombre del producto asociado a la guía.
 * @property remitenteIdentificacion Identificación del remitente.
 * @property remitenteNombre Nombre del remitente.
 * @property remitenteDireccion Dirección del remitente.
 * @property remitenteTelefono Número de teléfono del remitente.
 * @property destinatarioIdentificacion Identificación del destinatario.
 * @property destinatarioNombre Nombre del destinatario.
 * @property destinatarioDireccion Dirección del destinatario.
 * @property destinatarioTelefono Número de teléfono del destinatario.
 * @property totalFlete Monto total del flete asociado a la guía.
 * @constructor Crea un objeto GuiaInfoDTO con la información proporcionada.
 *
 * @author Julian Silva
 */
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
