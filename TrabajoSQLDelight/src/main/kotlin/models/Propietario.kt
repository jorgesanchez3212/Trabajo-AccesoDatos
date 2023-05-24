package models

import kotlinx.serialization.Serializable
import javax.persistence.*

@Serializable
@Entity
@Table( name = "Propietario")
data class Propietario(
    @Id
    @GeneratedValue
    val id : Int,
    val dni : String,
    val nombre: String,
    val apellidos : String,
    val teléfono : String,
    val vehiculos : List<Vehiculo>

) {
}