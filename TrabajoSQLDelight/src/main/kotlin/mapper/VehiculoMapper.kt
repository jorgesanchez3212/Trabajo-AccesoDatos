package mapper

import dto.TrabajadorDTO
import dto.VehiculoDTO
import models.Trabajador
import models.Vehiculo
import java.time.LocalDate


fun toVehiculoDTO(vehiculo: Vehiculo) : VehiculoDTO {
    return VehiculoDTO(
        vehiculo.id,
        vehiculo.marca,
        vehiculo.modelo,
        vehiculo.matricula,
        vehiculo.fechaMatriculacion.toString(),
        vehiculo.fechaUltimaRevision.toString()
    )
}


fun toVehiculo(vehiculo: VehiculoDTO) : Vehiculo {
    return Vehiculo(
        vehiculo.id,
        vehiculo.marca,
        vehiculo.modelo,
        vehiculo.matricula,
        LocalDate.parse(vehiculo.fechaMatriculacion),
        LocalDate.parse(vehiculo.fechaUltimaRevision)
    )
}