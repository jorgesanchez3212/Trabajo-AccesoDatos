package models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Serializable
@Entity
@Table(name = "Cita")
data class Cita(
    @Id
    @GeneratedValue
    val id : Int,
    val fechaHora : LocalDateTime,
    @OneToOne
    @JoinColumn(name = "idTrabajador", referencedColumnName = "id", nullable = false)
    val idTrabajador : Trabajador,
    @OneToOne
    @JoinColumn(name = "idVehiculo", referencedColumnName = "id", nullable = false)
    val idVehiculo : Vehiculo,
    @OneToOne
    @JoinColumn(name = "idPropietario", referencedColumnName = "id", nullable = false)
    val idPropietario : Propietario

) {
}