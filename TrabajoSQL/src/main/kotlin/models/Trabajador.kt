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
    var nombre : String,
    var telefono : Int,
    var email : String,
    var username : String,
    var contraseña : ByteArray,
    @Serializable(with = LocalDateSerializer::class)
    var fechaContratacion : LocalDate,
    var especialidad : String,
    var salario : Int,
    var responsable : Boolean

) {
    enum class Especialidad(){
        ELECTRICIDAD, MOTOR, MECANICA, INTERIOR
    }
}