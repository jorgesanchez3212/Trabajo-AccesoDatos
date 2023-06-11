package repositories.cita

import db.HibernateManager
import exception.CitaException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Cita
import models.Trabajador
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.*
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {  }

class CitaRepository : ICitaRepository {
    private val logger = KotlinLogging.logger {}

    override suspend fun findAll(): Result<Flow<Cita>> {
        logger.info { "Buscando todas las citas" }
        return try {
            var lista = mutableListOf<Cita>()
            HibernateManager.query {
                val query: TypedQuery<Cita> = HibernateManager.manager.createNamedQuery(
                    "Cita.findAll",
                    Cita::class.java
                )
                lista = query.resultList
            }
            Result.success(lista.asFlow())
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido obtener la lista de citas"))
        }
    }

    override suspend fun findById(id: UUID): Result<Cita?> {
        logger.info { "Buscando la cita con id $id" }
        return try {
            var encontrado: Cita? = null
            HibernateManager.query {
                encontrado = HibernateManager.manager.find(Cita::class.java, id)
            }
            Result.success(encontrado)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido encontrar la cita con el ID $id"))
        }
    }

    override suspend fun save(entity: Cita): Result<Unit> {
        logger.info { "Insertando la cita $entity" }
        return try {
            HibernateManager.transaction {
                val cita = HibernateManager.manager.merge(entity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido insertar la cita $entity"))
        }
    }

    override suspend fun update(entity: Cita): Result<Unit> {
        logger.info { "Actualizar la cita $entity" }
        return try {
            HibernateManager.transaction {
                val existing = HibernateManager.manager.find(Cita::class.java, entity.uuid)
                if (existing != null) {
                    existing.fechaHora = entity.fechaHora
                    existing.idTrabajador = entity.idTrabajador
                    existing.idPropietario = entity.idPropietario
                    existing.idVehiculo = entity.idVehiculo

                    HibernateManager.manager.merge(existing)
                    logger.info { "Cita actualizado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.info { "Cita no encontrado" }
                    Result.failure(CitaException("No se encontró ninguna cita con el UUID: ${entity.uuid}."))
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido actualizar la cita con el UUID: ${entity.uuid}"))
        }
    }

    override suspend fun delete(_id: UUID): Result<Unit> {
        logger.info { "Eliminando cita" }
        return try {
            HibernateManager.transaction {
                val cita = HibernateManager.manager.find(Cita::class.java, _id)
                if (cita != null) {
                    HibernateManager.manager.remove(cita)
                    logger.info { "Cita eliminado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.info { "Cita no encontrado" }
                    Result.failure(CitaException("No se encontró ninguna cita con el UUID: $_id."))
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido eliminar la cita con el UUID: $_id"))
        }
    }

    override suspend fun findByTrabajadorAndIntervalo(trabajador: Trabajador, fechaHora: LocalDateTime): Result<List<Cita>> {
        logger.info { "Buscando citas por trabajador y intervalo" }
        return try {
            val query: TypedQuery<Cita> = HibernateManager.manager.createQuery(
                "SELECT c FROM Cita c WHERE c.idTrabajador = :trabajador AND c.fechaHora >= :fechaHora AND c.fechaHora < :fechaHoraLimite",
                Cita::class.java
            )
            query.setParameter("trabajador", trabajador)
            query.setParameter("fechaHora", fechaHora)
            query.setParameter("fechaHoraLimite", fechaHora.plusMinutes(30))
            val citas = query.resultList
            Result.success(citas)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido encontrar citas para el trabajador con ID ${trabajador.uuid} y el intervalo de tiempo especificado"))
        }
    }

    override suspend fun findByIntervalo(fechaHora: LocalDateTime): Result<List<Cita>> {
        logger.info { "Buscando citas por intervalo de fecha y hora" }
        return try {
            val intervaloInicio = fechaHora
            val intervaloFin = fechaHora.plusMinutes(30)

            val query: TypedQuery<Cita> = HibernateManager.manager.createQuery(
                "SELECT c FROM Cita c WHERE c.fechaHora >= :intervaloInicio AND c.fechaHora < :intervaloFin",
                Cita::class.java
            )
            query.setParameter("intervaloInicio", intervaloInicio)
            query.setParameter("intervaloFin", intervaloFin)

            val citas = query.resultList
            Result.success(citas)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido encontrar citas para el intervalo de tiempo especificado"))
        }
    }

    suspend fun deleteAll(): Result<Unit> {
        logger.info { "Eliminando todas las citas" }
        return try {
            HibernateManager.transaction {
                val query = HibernateManager.manager.createQuery("delete from Cita")
                query.executeUpdate()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido borrar las citas"))
        }
    }
}
