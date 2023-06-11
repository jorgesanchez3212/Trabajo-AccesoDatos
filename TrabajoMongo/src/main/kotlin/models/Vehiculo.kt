package models


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import java.time.LocalDate
import java.util.UUID

@Serializable
data class Vehiculo(
    @BsonId @Contextual
    val _id : String = newId<Trabajador>().toString(),
    val marca: String,
    val modelo : String,
    val matricula : String,
    @Contextual
    val fechaMatriculacion : LocalDate,
    @Contextual
    val fechaUltimaRevision : LocalDate

) {
}