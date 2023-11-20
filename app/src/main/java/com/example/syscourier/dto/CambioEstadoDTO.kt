/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para gestionar cambios de estado en una guía.
 *
 * @property guiaId Identificador de la guía asociada al cambio de estado.
 * @property codEstado Código del estado al que se desea cambiar.
 * @property motivo Motivo relacionado con el cambio de estado (puede ser nulo).
 * @property observaciones Observaciones adicionales sobre el cambio de estado.
 * @constructor Crea un objeto CambioEstadoDTO.
 *
 * @author Julian Silva
 */
package com.example.syscourier.dto

data class CambioEstadoDTO(
    val guiaId: Int,
    val codEstado: Int,
    val motivo: String?,
    val observaciones: String
)
