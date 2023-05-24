package models

import kotlinx.serialization.Serializable
import java.time.LocalDate
import javax.persistence.*

@Serializable
@Entity
@Table(name = "Vehiculo")
@NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v")
data class Vehiculo(
    @Id
    @GeneratedValue
    val id : Int,
    val marca: String,
    val modelo : String,
    val matricula : String,
    val fechaMatriculacion : LocalDate,
    val fechaUltimaRevision : LocalDate
) {
}