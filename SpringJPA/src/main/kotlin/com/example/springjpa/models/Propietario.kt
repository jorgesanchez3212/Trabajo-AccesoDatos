package com.example.springjpa.models


import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlinx.serialization.Serializable
import java.util.*

@Entity
@Table(name = "Propietario")
@Serializable
data class Propietario(
    @Id
    var uuid: UUID = UUID.randomUUID(),
    var dni : String,
    var nombre: String,
    var apellidos : String,
    var teléfono : String
) {
}