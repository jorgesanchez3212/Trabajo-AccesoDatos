package mapper

import dto.TrabajadorDTO
import models.Trabajador
import java.time.LocalDate

fun toTrabajadorDTO(trabajador: Trabajador) : TrabajadorDTO{
    return TrabajadorDTO(
        trabajador.id,
        trabajador.nombre,
        trabajador.teléfono,
        trabajador.email,
        trabajador.username,
        trabajador.contraseña,
        trabajador.fechaContratacion.toString(),
        trabajador.especialidad,
        trabajador.salario,
        trabajador.responsable,
    )
}


fun toTrabajador(trabajador: TrabajadorDTO) : Trabajador{
    return Trabajador(
        trabajador.id,
        trabajador.nombre,
        trabajador.teléfono,
        trabajador.email,
        trabajador.username,
        trabajador.contraseña,
        LocalDate.parse(trabajador.fechaContratacion),
        trabajador.especialidad,
        trabajador.salario,
        trabajador.responsable,
    )
}