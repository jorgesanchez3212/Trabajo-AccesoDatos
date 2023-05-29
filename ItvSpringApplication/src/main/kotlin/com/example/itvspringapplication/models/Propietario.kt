package com.example.itvspringapplication.models

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Serializable
@Document(collection = "propietario")
data class Propietario(
    @Id
    val _id : String = ObjectId.get().toString(),
    val dni : String,
    val nombre: String,
    val apellidos : String,
    val teléfono : String
) {
}