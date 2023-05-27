package models

import kotlinx.serialization.Serializable
import java.time.LocalDate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.LocalDateSerializer
import utils.serializer.UUIDSerializer
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "Trabajador")
@NamedQueries(
    NamedQuery(name ="Trabajador.findAll", query = "select t from Trabajador t"),
    NamedQuery(name = "Trabajador.findByEmail", query = "select t from Trabajador t where t.email= :email"),
    NamedQuery(name = "Trabajador.findByUsername", query = "select t from Trabajador t where t.username= :username")

)
@Serializable
data class Trabajador(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)
    var uuid:UUID = UUID.randomUUID(),
    val nombre : String,
    val teléfono : Int,
    val email : String,
    val username : String,
    val contraseña : ByteArray,
    @Serializable(with = LocalDateSerializer::class)
    val fechaContratacion : LocalDate,
    val especialidad : String,
    val salario : Int,
    val responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}