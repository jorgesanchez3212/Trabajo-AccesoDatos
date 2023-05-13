package dto

import kotlinx.serialization.Serializable

@Serializable
data class VehiculoDTO(
    val id : Int,
    val marca: String,
    val modelo : String,
    val matricula : String,
    val fechaMatriculacion : String,
    val fechaUltimaRevision : String

) {
}