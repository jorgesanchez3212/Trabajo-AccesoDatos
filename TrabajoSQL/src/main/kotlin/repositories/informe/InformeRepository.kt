package repositories.informe

import db.HibernateManager
import exception.InformeException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Informe
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

private var logger = KotlinLogging.logger {  }
class InformeRepository : IInformeRepository {
    private val logger = KotlinLogging.logger {}

    override suspend fun findAll(): Result<Flow<Informe>> {
        logger.info { "Buscando todos los informes" }
        return try {
            var lista = mutableListOf<Informe>()
            HibernateManager.query {
                val query: TypedQuery<Informe> = HibernateManager.manager.createNamedQuery(
                    "Informe.findAll",
                    Informe::class.java
                )
                lista = query.resultList
            }
            Result.success(lista.asFlow())
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido obtener la lista de informes"))
        }
    }

    override suspend fun findById(id: UUID): Result<Informe?> {
        logger.info { "Buscando el informe con id $id" }
        return try {
            var encontrado: Informe? = null
            HibernateManager.query {
                encontrado = HibernateManager.manager.find(Informe::class.java, id)
            }
            Result.success(encontrado)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido encontrar el informe con el ID $id"))
        }
    }

    override suspend fun save(entity: Informe): Result<Unit> {
        logger.info { "Insertando el informe $entity" }
        return try {
            HibernateManager.transaction {
                val informe = HibernateManager.manager.merge(entity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido insertar el informe $entity"))
        }
    }

    override suspend fun update(entity: Informe): Result<Unit> {
        logger.info { "Actualizar el informe $entity" }
        return try {
            HibernateManager.transaction {
                val existing = HibernateManager.manager.find(Informe::class.java, entity.uuid)
                if (existing != null) {
                    existing.frenado = entity.frenado
                    existing.contaminación = entity.contaminación
                    existing.aptoFrenado = entity.aptoFrenado
                    existing.apto = entity.apto
                    existing.luces = entity.luces
                    existing.idTrabajador = entity.idTrabajador
                    existing.idVehiculo = entity.idVehiculo
                    existing.idPropietario = entity.idPropietario

                    HibernateManager.manager.merge(existing)
                    logger.info { "Informe actualizado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.info { "Informe no encontrado" }
                    Result.failure(InformeException("No se encontró ningún informe con el UUID: ${entity.uuid}."))
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido actualizar el informe con el UUID: ${entity.uuid}"))
        }
    }

    override suspend fun delete(_id: UUID): Result<Unit> {
        logger.info { "Eliminando informe" }
        return try {
            HibernateManager.transaction {
                val informe = HibernateManager.manager.find(Informe::class.java, _id)
                if (informe != null) {
                    HibernateManager.manager.remove(informe)
                    logger.info { "Informe eliminado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.info { "Informe no encontrado" }
                    Result.failure(InformeException("No se encontró ningún informe con el UUID: $_id."))
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido eliminar el informe con el UUID: $_id"))
        }
    }

    suspend fun deleteAll(): Result<Unit> {
        logger.info { "Eliminando todos los informes" }
        return try {
            HibernateManager.transaction {
                val query = HibernateManager.manager.createQuery("delete from Informe")
                query.executeUpdate()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido borrar los informes"))
        }
    }
}
