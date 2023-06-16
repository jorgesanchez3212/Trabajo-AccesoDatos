package models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId

@Serializable
data class Informe(
    @BsonId
    val _id : String = newId<Informe>().toString(),
    val frenado : Int,
    val contaminación : Double,
    val aptoFrenado: Boolean,
    val luces : Boolean,
    val apto : Boolean,
    val idTrabajador: String,
    val idVehiculo: String,
    val idPropietario: String,

    ) {
}