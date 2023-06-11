package repositories.propietario

import db.HibernateManager
import db.HibernateManager.manager
import exception.PropietarioException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Propietario
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

private var logger = KotlinLogging.logger {  }
class PropietarioRepository : IPropietarioRepository {

    override suspend fun findAll(): Result<Flow<Propietario>> {
        logger.info { "Buscando todos los propietarios" }
        return try {
            var lista = mutableListOf<Propietario>()
            HibernateManager.query {
                var query: TypedQuery<Propietario> = manager.createNamedQuery(
                    "Propietario.findAll",
                    Propietario::class.java
                )
                lista = query.resultList
            }
            Result.success(lista.asFlow())
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido obtener todos los propietarios"))
        }
    }

    override suspend fun findById(id: UUID): Result<Propietario?> {
        logger.info { "Buscando el propietario con id $id" }
        return try {
            var encontrado: Propietario? = null
            HibernateManager.query {
                encontrado = manager.find(Propietario::class.java, id)
            }
            Result.success(encontrado)
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido encontrar el propietario con el ID $id"))
        }
    }

    override suspend fun save(entity: Propietario): Result<Unit> {
        logger.info { "Insertando el propietario $entity" }
        return try {
            HibernateManager.transaction {
                manager.merge(entity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido insertar el propietario $entity"))
        }
    }

    override suspend fun update(entity: Propietario): Result<Unit> {
        logger.info { "Actualizar el propietario $entity" }
        return try {
            HibernateManager.transaction {
                var propietarioExisting =
                    HibernateManager.manager.find(Propietario::class.java, entity.uuid)
                if (propietarioExisting != null) {
                    propietarioExisting.dni = entity.dni
                    propietarioExisting.nombre = entity.nombre
                    propietarioExisting.apellidos = entity.apellidos
                    propietarioExisting.teléfono = entity.teléfono

                    HibernateManager.manager.merge(propietarioExisting)
                    logger.info { "Propietario actualizado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.info { "Propietario no encontrado" }
                    Result.failure(PropietarioException("Propietario no encontrado"))
                }
            }
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido actualizar el propietario $entity"))
        }
    }

    override suspend fun delete(_id: UUID): Result<Unit> {
        logger.info { "Eliminando propietario" }
        return try {
            HibernateManager.transaction {
                var propietario = HibernateManager.manager.find(Propietario::class.java, _id)
                if (propietario != null) {
                    HibernateManager.manager.remove(propietario)
                    logger.info { "Propietario eliminado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.info { "Propietario no encontrado" }
                    Result.failure(PropietarioException("No se ha podido borrar el propietario $_id"))
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido borrar el propietario $_id"))
        }
    }

    suspend fun deleteAll(): Result<Unit> {
        return try {
            HibernateManager.transaction {
                var query = HibernateManager.manager.createQuery("delete from Propietario ")
                query.executeUpdate()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido borrar los propietarios"))
        }
    }
}
