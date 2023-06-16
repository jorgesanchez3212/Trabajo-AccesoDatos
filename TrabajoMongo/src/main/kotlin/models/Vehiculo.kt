package models


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import serializers.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class Vehiculo(
    @BsonId
    val _id : String = newId<Trabajador>().toString(),
    val marca: String,
    val modelo : String,
    val matricula : String,
    @Serializable(with = LocalDateSerializer::class)
    val fechaMatriculacion : LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val fechaUltimaRevision : LocalDate

) {
}