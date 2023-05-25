package repositories.cita

import kotlinx.coroutines.flow.Flow
import models.Cita
import repositories.CrudRepository
import java.time.LocalDateTime

interface ICitaRepository : CrudRepository<Cita,String> {
    suspend fun findByTrabajadorAndIntervalo(trabajador: String, fechaHora: LocalDateTime): List<Cita>
    suspend fun findByIntervalo(fechaHora: LocalDateTime): List<Cita>
}