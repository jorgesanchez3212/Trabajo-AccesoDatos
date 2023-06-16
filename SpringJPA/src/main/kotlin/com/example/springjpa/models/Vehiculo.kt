package com.example.springjpa.models

import com.example.springjpa.serializers.LocalDateSerializer
import com.example.springjpa.serializers.UUIDSerializer
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "vehiculo")
@Serializable
data class Vehiculo(
    @Id @Serializable(with = UUIDSerializer::class)
    var uuid: UUID,
    var marca: String,
    var modelo : String,
    var matricula : String,
    @Serializable(with = LocalDateSerializer::class)
    var fechaMatriculacion : LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    var fechaUltimaRevision : LocalDate

) {
}