package repositories.cita

import kotlinx.coroutines.flow.Flow
import models.Cita
import repositories.CrudRepository
import java.time.LocalDateTime

interface ICitaRepository : CrudRepository<Cita,String> {
    suspend fun findByTrabajadorAndIntervalo(trabajador: String, fechaHora: LocalDateTime): Result<List<Cita>>
    suspend fun findByIntervalo(fechaHora: LocalDateTime): Result<List<Cita>>
}