package com.example.itvspringapplication.models

import com.example.itvspringapplication.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Serializable
@Document(collection = "vehiculo")
data class Vehiculo(
    @Id
    val _id : String = ObjectId.get().toString(),
    val marca: String,
    val modelo : String,
    val matricula : String,
    @Serializable(with = LocalDateSerializer::class)
    val fechaMatriculacion : LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val fechaUltimaRevision : LocalDate

) {
}