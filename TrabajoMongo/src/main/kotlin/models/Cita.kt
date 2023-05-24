package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import java.time.LocalDateTime

@Serializable
data class Cita(
    @BsonId @Contextual
    val _id : String = newId<Cita>().toString(),
    val fechaHora : LocalDateTime,
    val idTrabajador : String,
    val idVehiculo : String,
    val idPropietario : String

) {
}