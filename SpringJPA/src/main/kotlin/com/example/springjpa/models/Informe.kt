package com.example.springjpa.models


import jakarta.persistence.*
import kotlinx.serialization.Serializable
import java.util.*

@Entity
@Table(name = "Informe")
@Serializable
data class Informe(
    @Id
    var uuid: UUID = UUID.randomUUID(),
    var frenado : Int,
    var contaminación : Double,
    var aptoFrenado: Boolean,
    var luces : Boolean,
    var apto : Boolean,
    @OneToOne
    @JoinColumn(name = "trabajador_id",nullable = false)
    var idTrabajador : Trabajador,
    @OneToOne
    @JoinColumn(name = "vehiculo_id",nullable = false)
    var idVehiculo : Vehiculo,
    @OneToOne
    @JoinColumn(name = "propietario_id",nullable = false)
    var idPropietario : Propietario

) {
}