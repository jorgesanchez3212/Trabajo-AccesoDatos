package com.example.itvspringapplication.models

import com.example.itvspringapplication.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

@Serializable
@Document(collection = "cita")
data class Cita(
    @Id
    val _id : String = ObjectId.get().toString(),
    @Serializable(with = LocalDateTimeSerializer::class)
    val fechaHora : LocalDateTime,
    @DocumentReference
    val idTrabajador : Trabajador,
    @DocumentReference
    val idVehiculo : Vehiculo,
    @DocumentReference
    val idPropietario : Propietario
) {
}