package models

import kotlinx.serialization.Serializable
import javax.persistence.*

@Serializable
@Entity
@Table(name = "Informe")
data class Informe(
    @Id
    @GeneratedValue
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