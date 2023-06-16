package com.example.itvspringapplication.models

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Serializable
@Document(collection = "informe")
data class Informe(
    @Id
    val _id : String = ObjectId.get().toString(),
    val frenado : Int,
    val contaminación : Double,
    val aptoFrenado: Boolean,
    val luces : Boolean,
    val apto : Boolean,
    @DocumentReference(lookup = "{ '_id' : ?#{#target} }")
    val idTrabajador: Trabajador,
    @DocumentReference(lookup = "{ '_id' : ?#{#target} }")
    val idVehiculo: Vehiculo,
    @DocumentReference(lookup = "{ '_id' : ?#{#target} }")
    val idPropietario: Propietario,
) {

}