package models


import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import java.time.LocalDate

data class Vehiculo(
    @BsonId
    val _id : String = newId<Trabajador>().toString(),
    val marca: String,
    val modelo : String,
    val matricula : String,
    val fechaMatriculacion : LocalDate,
    val fechaUltimaRevision : LocalDate

) {
}