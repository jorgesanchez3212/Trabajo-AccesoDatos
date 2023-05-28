package models

import kotlinx.serialization.Serializable
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.UUIDSerializer
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Informe")
@NamedQueries(
    NamedQuery(name ="Informe.findAll", query = "select i from Informe i"),
)
@Serializable
data class Informe(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID = UUID.randomUUID(),
    var frenado : Int,
    var contaminación : Double,
    var aptoFrenado: Boolean,
    var luces : Boolean,
    var apto : Boolean,
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