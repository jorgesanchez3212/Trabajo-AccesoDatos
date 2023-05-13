package dto

import kotlinx.serialization.Serializable

@Serializable
data class TrabajadorDTO(
    val id : Int,
    val nombre : String,
    val teléfono : Int,
    val email : String, // Es unico
    val username : String,
    val contraseña : String, //Cifrada con Bcrypt
    val fechaContratacion : String,
    val especialidad : String,
    val salario : Int,
    val responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}