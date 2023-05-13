package mapper

import dto.CitaDTO
import models.Cita
import java.time.LocalDateTime


fun toCitaDTO(cita: Cita) : CitaDTO {
    return CitaDTO(
        cita.id,
        cita.fechaHora.toString(),
        cita.idTrabajador,
        cita.idVehiculo,
        cita.idPropietario,
    )
}


fun toCita(cita: CitaDTO) : Cita {
    return Cita(
        cita.id,
        LocalDateTime.parse(cita.fechaHora),
        cita.idTrabajador,
        cita.idVehiculo,
        cita.idPropietario,
    )
}