package com.example.itvspringapplication.models

import com.example.itvspringapplication.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Serializable
@Document(collection = "trabajador")
data class Trabajador(
    @Id
    val _id : String = ObjectId.get().toString(),
    val nombre : String,
    val teléfono : Int,
    val email : String,
    val username : String,
    val contraseña : ByteArray,
    @Serializable(with = LocalDateSerializer::class)
    val fechaContratacion : LocalDate,
    val especialidad : String,
    val salario : Int,
    val responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}