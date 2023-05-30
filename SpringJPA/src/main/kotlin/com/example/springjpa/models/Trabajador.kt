package com.example.springjpa.models

import com.example.springjpa.serializers.LocalDateSerializer
import jakarta.persistence.*
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "trabajador")
@Serializable
data class Trabajador(
    @Id
    var uuid: UUID = UUID.randomUUID(),
    var nombre : String,
    var telefono : Int,
    var email : String,
    var username : String,
    var contraseña : ByteArray,
    @Serializable(with = LocalDateSerializer::class)
    var fechaContratacion : LocalDate,
    var especialidad : String,
    var salario : Int,
    var responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}