package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import serializers.LocalDateSerializer
import java.time.LocalDate

@Serializable
@SerialName("Trabajador")
data class Trabajador(
    @BsonId @Contextual
    val _id : String = newId<Trabajador>().toString(),
    val nombre : String,
    val teléfono : Int,
    val email : String,
    val username : String,
    val contraseña : ByteArray,
    @Serializable(LocalDateSerializer::class)
    val fechaContratacion : LocalDate,
    val especialidad : String,
    val salario : Int,
    val responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}