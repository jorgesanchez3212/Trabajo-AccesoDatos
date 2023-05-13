package dto
import kotlinx.serialization.Serializable

@Serializable
data class CitaDTO(
    val id : Int,
    val fechaHora : String,
    val idTrabajador : Int,
    val idVehiculo : Int,
    val idPropietario : Int

) {
}