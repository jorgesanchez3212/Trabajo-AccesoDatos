package models

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Vehiculo(
    val id : Int,
    val marca: String,
    val modelo : String,
    val matricula : String,
    val fechaMatriculacion : LocalDate,
    val fechaUltimaRevision : LocalDate

) {
}