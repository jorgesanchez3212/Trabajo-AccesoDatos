package models

import kotlinx.serialization.Serializable
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.LocalDateSerializer
import utils.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Vehiculo")
@NamedQueries(
    NamedQuery(name ="Vehiculo.findAll", query = "select v from Vehiculo v"),

    )
@Serializable
data class Vehiculo(
    @Id
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID = UUID.randomUUID(),
    var marca: String,
    var modelo : String,
    var matricula : String,
    @Serializable(with = LocalDateSerializer::class)
    var fechaMatriculacion : LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    var fechaUltimaRevision : LocalDate

) {
}