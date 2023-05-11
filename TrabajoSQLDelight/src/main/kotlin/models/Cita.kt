package models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Cita(
    val id : Int,
    val fechaHora : LocalDateTime,
    val idTrabajador : Int,
    val idVehiculo : Int,
    val idPropietario : Int

) {
}