package mapper

import dto.PropietarioDTO
import models.Propietario



fun toPropietarioDTO(propietario: Propietario) : PropietarioDTO {
    return PropietarioDTO(
        propietario.id,
        propietario.dni,
        propietario.nombre,
        propietario.apellidos,
        propietario.teléfono,
        propietario.vehiculos
    )
}


fun toPropietario(propietario: PropietarioDTO) : Propietario {
    return Propietario(
        propietario.id,
        propietario.dni,
        propietario.nombre,
        propietario.apellidos,
        propietario.teléfono,
        propietario.vehiculos
    )
}