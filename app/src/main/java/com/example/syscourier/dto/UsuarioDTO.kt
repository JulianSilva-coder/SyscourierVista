/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para la información del usuario en el sistema de mensajería.
 *
 * @property correo Correo electrónico del usuario.
 * @property password Contraseña del usuario.
 * @constructor Crea un objeto UsuarioDTO con la información proporcionada.
 *
 * @author Julian Silva
 */
package com.example.syscourier.dto

class UsuarioDTO(
    val correo: String,
    val password: String
)
