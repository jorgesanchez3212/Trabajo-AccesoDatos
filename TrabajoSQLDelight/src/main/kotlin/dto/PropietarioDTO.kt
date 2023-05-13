package dto

import kotlinx.serialization.Serializable
import models.Vehiculo

@Serializable
data class PropietarioDTO(
    val id : Int,
    val dni : String,
    val nombre: String,
    val apellidos : String,
    val teléfono : String,
    val vehiculos : List<Vehiculo>

) {
}