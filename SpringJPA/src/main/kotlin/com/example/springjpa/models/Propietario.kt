package com.example.springjpa.models


import com.example.springjpa.serializers.UUIDSerializer
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Entity
@Table(name = "Propietario")
@Serializable
data class Propietario(
    @Id @Serializable(with = UUIDSerializer::class)
    var uuid: UUID ,
    var dni : String,
    var nombre: String,
    var apellidos : String,
    var teléfono : String
) {
}