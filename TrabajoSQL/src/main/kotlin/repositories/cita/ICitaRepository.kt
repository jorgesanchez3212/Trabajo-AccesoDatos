package repositories.cita

import kotlinx.coroutines.flow.Flow
import models.Cita
import models.Vehiculo
import repositories.CrudRepository
import java.time.LocalDateTime
import java.util.*

interface ICitaRepository : CrudRepository<Cita, UUID> {
    suspend fun findByTrabajadorAndIntervalo(trabajador: UUID, fechaHora: LocalDateTime): List<Cita>
    suspend fun findByIntervalo(fechaHora: LocalDateTime): List<Cita>
}