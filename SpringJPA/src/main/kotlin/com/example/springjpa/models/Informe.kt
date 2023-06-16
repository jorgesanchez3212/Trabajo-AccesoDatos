package com.example.springjpa.models


import com.example.springjpa.serializers.UUIDSerializer
import jakarta.persistence.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Entity
@Table(name = "Informe")
@Serializable
data class Informe(
    @Id @Serializable(with = UUIDSerializer::class)
    var uuid: UUID ,
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