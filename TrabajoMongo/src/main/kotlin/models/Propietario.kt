package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId

data class Propietario(
    @BsonId
    val _id : String = newId<Propietario>().toString(),
    val dni : String,
    val nombre: String,
    val apellidos : String,
    val teléfono : String
    ) {
}