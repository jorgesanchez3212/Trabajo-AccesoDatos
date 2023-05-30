package com.example.springjpa.models


import com.example.springjpa.serializers.LocalDateTimeSerializer
import jakarta.persistence.*
import kotlinx.serialization.Serializable

import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "cita")
@Serializable
data class Cita(
    @Id
    var uuid: UUID = UUID.randomUUID(),
    @Serializable(with = LocalDateTimeSerializer::class)
    var fechaHora : LocalDateTime,
    @OneToOne
    @JoinColumn(name = "trabajador_id",nullable = false)
    var trabajador : Trabajador,
    @OneToOne
    @JoinColumn(name = "vehiculo_id",nullable = false)
    var vehiculo : Vehiculo,
    @OneToOne
    @JoinColumn(name = "propietario_id",nullable = false)
    var propietario : Propietario

) {
}