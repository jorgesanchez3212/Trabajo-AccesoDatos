package repositories.cita

import db.HibernateManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Cita
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.*
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {  }

class CitaRepository : ICitaRepository {
    override suspend fun findAll(): Flow<Cita> {
        logger.info { "Buscando todas las citas" }
        var lista = mutableListOf<Cita>()
        HibernateManager.query {
            var query : TypedQuery<Cita> = HibernateManager.manager.createNamedQuery("Cita.findAll",
                Cita::class.java)
            lista = query.resultList
        }
        return lista.asFlow()
    }

    override suspend fun findById(id: UUID): Cita? {
        logger.info { "Buscando la cita con id $id" }
        var encontrado : Cita? = null
        HibernateManager.query {
            encontrado = HibernateManager.manager.find(Cita::class.java, id)
        }
        return encontrado
    }

    override suspend fun save(entity: Cita) {
        logger.info { "Insertando la cita $entity" }
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
    }

    override suspend fun update(entity: Cita) {
        logger.info { "Actualizar la cita $entity"}
        HibernateManager.transaction {
            var existing = HibernateManager.manager.find(Cita::class.java,entity.uuid)
            if (existing != null){
                existing.fechaHora = entity.fechaHora
                existing.idTrabajador = entity.idTrabajador
                existing.idPropietario = entity.idPropietario
                existing.idVehiculo = entity.idVehiculo

                HibernateManager.manager.merge(existing)
                logger.info { "Cita actualizado correctamente" }
            }else{
                logger.info { "Cita no encontrado" }

            }
        }    }

    override suspend fun delete(_id: UUID) {
        logger.info { "Eliminando cita" }
        var cita : Cita? = null
        HibernateManager.transaction {
            cita = HibernateManager.manager.find(Cita::class.java,_id)
            if (cita != null){
                HibernateManager.manager.remove(cita)
                logger.info { "Cita eliminado correctamente" }
            }else{
                logger.info { "Cita no encontrado" }
            }

        }
    }

    override suspend fun findByTrabajadorAndIntervalo(trabajador: UUID, fechaHora: LocalDateTime): List<Cita> {
        logger.info { "Buscando citas por trabajador y intervalo" }

        val query: TypedQuery<Cita> = HibernateManager.manager.createQuery(
            "SELECT c FROM Cita c WHERE c.idTrabajador = :trabajador AND c.fechaHora >= :fechaHora AND c.fechaHora < :fechaHoraLimite",
            Cita::class.java
        )
        query.setParameter("trabajador", trabajador)
        query.setParameter("fechaHora", fechaHora)
        query.setParameter("fechaHoraLimite", fechaHora.plusMinutes(30))

        return query.resultList
    }

    override suspend fun findByIntervalo(fechaHora: LocalDateTime): List<Cita> {
        logger.info { "Buscando citas por intervalo de fecha y hora" }

        val intervaloInicio = fechaHora
        val intervaloFin = fechaHora.plusMinutes(30)

        val query: TypedQuery<Cita> = HibernateManager.manager.createQuery(
            "SELECT c FROM Cita c WHERE c.fechaHora >= :intervaloInicio AND c.fechaHora < :intervaloFin",
            Cita::class.java
        )
        query.setParameter("intervaloInicio", intervaloInicio)
        query.setParameter("intervaloFin", intervaloFin)

        return query.resultList
    }
}