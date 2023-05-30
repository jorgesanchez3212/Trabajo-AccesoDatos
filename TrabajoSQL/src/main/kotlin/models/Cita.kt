package models

import kotlinx.serialization.Serializable
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.LocalDateTimeSerializer
import utils.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Cita")
@NamedQueries(
    NamedQuery(name ="Cita.findAll", query = "select c from Cita c"),
)
@Serializable
data class Cita(
    @Id
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID = UUID.randomUUID(),
    @Serializable(with = LocalDateTimeSerializer::class)
    var fechaHora : LocalDateTime,
    @OneToOne
    @JoinColumn(name = "trabajador_id",nullable = false)
    var idTrabajador : Trabajador,
    @OneToOne
    @JoinColumn(name = "vehiculo_id",nullable = false)
    var idVehiculo : Vehiculo,
    @OneToOne
    @JoinColumn(name = "propietario_id",nullable = false)
    var idPropietario : Propietario

) {
}