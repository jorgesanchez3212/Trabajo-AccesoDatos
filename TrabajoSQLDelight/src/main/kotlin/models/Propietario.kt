package models

import kotlinx.serialization.Serializable

@Serializable
data class Propietario(
    val id : Int,
    val dni : String,
    val nombre: String,
    val apellidos : String,
    val teléfono : String,
    val vehiculos : List<Vehiculo>

) {
}