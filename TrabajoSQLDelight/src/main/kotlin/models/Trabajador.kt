package models

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Trabajador(
    val id : Int,
    val nombre : String,
    val teléfono : Int,
    val email : String, // Es unico
    val username : String,
    val contraseña : String, //Cifrada con Bcrypt
    val fechaContratacion : LocalDate,
    val especialidad : Especialidad,
    val salario : Int,
    val responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}