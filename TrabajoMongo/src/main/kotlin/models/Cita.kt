package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import serializers.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Cita(
    @BsonId
    val _id : String = newId<Cita>().toString(),
    @Serializable(with = LocalDateTimeSerializer::class)
    val fechaHora : LocalDateTime,
    val idTrabajador : String,
    val idVehiculo : String,
    val idPropietario : String

) {
}