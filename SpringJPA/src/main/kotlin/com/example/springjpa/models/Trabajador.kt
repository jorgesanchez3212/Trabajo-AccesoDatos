package com.example.springjpa.models

import com.example.springjpa.serializers.LocalDateSerializer
import com.example.springjpa.serializers.UUIDSerializer
import jakarta.persistence.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "trabajador")
@Serializable
data class Trabajador(
    @Id @Serializable(with = UUIDSerializer::class)
    var uuid: UUID,
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