package models

import kotlinx.serialization.Contextual
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Trabajador(
    @BsonId @Contextual
    val _id : String = newId<Trabajador>().toString(),
    val nombre : String,
    val teléfono : Int,
    val email : String,
    val username : String,
    val contraseña : ByteArray,
    val fechaContratacion : LocalDate,
    val especialidad : String,
    val salario : Int,
    val responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}