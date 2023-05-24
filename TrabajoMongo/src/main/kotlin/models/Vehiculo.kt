package models


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import java.time.LocalDate

@Serializable
data class Vehiculo(
    @BsonId @Contextual
    val _id : String = newId<Trabajador>().toString(),
    val marca: String,
    val modelo : String,
    val matricula : String,
    val fechaMatriculacion : LocalDate,
    val fechaUltimaRevision : LocalDate

) {
}