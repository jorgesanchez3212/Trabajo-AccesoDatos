package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import java.time.LocalDateTime


data class Cita(
    @BsonId
    val _id : String = newId<Cita>().toString(),
    val fechaHora : LocalDateTime,
    val idTrabajador : String,
    val idVehiculo : String,
    val idPropietario : String

) {
}