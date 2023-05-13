package dto

import kotlinx.serialization.Serializable

@Serializable
data class InformeDTO(
    val frenado : Int,
    val contaminación : Double,
    val aptoFrenado: Boolean,
    val luces : Boolean,
    val apto : Boolean,
    val idTrabajador: Int,
    val idVehiculo: Int,
    val idPropietario: Int,

    ) {
}