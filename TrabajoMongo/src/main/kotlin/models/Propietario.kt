package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId

@Serializable
data class Propietario(
    @BsonId @Contextual
    val _id : String = newId<Propietario>().toString(),
    val dni : String,
    val nombre: String,
    val apellidos : String,
    val teléfono : String
    ) {
}