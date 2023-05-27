package models

import kotlinx.serialization.Serializable
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.UUIDSerializer
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Propietario")
@NamedQueries(
    NamedQuery(name ="Propietario.findAll", query = "select p from Propietario p"),
)
@Serializable
data class Propietario(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID = UUID.randomUUID(),
    val dni : String,
    val nombre: String,
    val apellidos : String,
    val teléfono : String
) {
}