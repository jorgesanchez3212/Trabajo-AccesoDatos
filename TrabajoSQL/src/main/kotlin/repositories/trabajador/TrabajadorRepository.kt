package repositories.trabajador

import db.HibernateManager
import db.HibernateManager.manager
import exception.TrabajadorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Trabajador
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery
class TrabajadorRepository : ITrabajadorRepository {
    private val logger = KotlinLogging.logger {}

    override suspend fun findByEmail(email: String): Result<Trabajador?> {
        logger.debug { "Buscando trabajador por su email" }
        return try {
            var encontrado: Trabajador? = null
            HibernateManager.query {
                val query: TypedQuery<Trabajador> = manager.createNamedQuery(
                    "Trabajador.findByEmail",
                    Trabajador::class.java
                )
                query.setParameter("email", email)
                encontrado = query.resultList.firstOrNull()
            }
            Result.success(encontrado)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido encontrar el trabajador por su email"))
        }
    }

    override suspend fun findByUsername(username: String): Result<Trabajador?> {
        logger.debug { "Buscando trabajador por su username" }
        return try {
            var encontrado: Trabajador? = null
            HibernateManager.query {
                val query: TypedQuery<Trabajador> = manager.createNamedQuery(
                    "Trabajador.findByUsername",
                    Trabajador::class.java
                )
                query.setParameter("username", username)
                encontrado = query.resultList.firstOrNull()
            }
            Result.success(encontrado)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido encontrar el trabajador por su username"))
        }
    }

    override suspend fun findById(id: UUID): Result<Trabajador?> {
        logger.debug { "Buscando trabajador por el id" }
        return try {
            var encontrado: Trabajador? = null
            HibernateManager.query {
                encontrado = manager.find(Trabajador::class.java, id)
            }
            Result.success(encontrado)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido encontrar el trabajador con el ID $id"))
        }
    }

    override suspend fun save(entity: Trabajador): Result<Unit> {
        logger.debug { "Insertando trabajador" }
        return try {
            HibernateManager.transaction {
                manager.merge(entity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido insertar el trabajador $entity"))
        }
    }

    override suspend fun update(entity: Trabajador): Result<Unit> {
        logger.debug { "Actualizando trabajador con UUID: ${entity.uuid}" }
        return try {
            HibernateManager.transaction {
                val existingTrabajador = manager.find(Trabajador::class.java, entity.uuid)
                if (existingTrabajador != null) {
                    existingTrabajador.nombre = entity.nombre
                    existingTrabajador.telefono = entity.telefono
                    existingTrabajador.email = entity.email
                    existingTrabajador.username = entity.username
                    existingTrabajador.contraseña = entity.contraseña
                    existingTrabajador.fechaContratacion = entity.fechaContratacion
                    existingTrabajador.especialidad = entity.especialidad
                    existingTrabajador.salario = entity.salario
                    existingTrabajador.responsable = entity.responsable

                    manager.merge(existingTrabajador)
                    logger.debug { "Trabajador actualizado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.debug { "No se encontró ningún trabajador con el UUID: ${entity.uuid}. No se realizó ninguna actualización." }
                    Result.failure(TrabajadorException("No se encontró ningún trabajador con el UUID: ${entity.uuid}."))
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido actualizar el trabajador con el UUID: ${entity.uuid}"))
        }
    }

    override suspend fun delete(uuid: UUID): Result<Unit> {
        logger.debug { "Eliminando trabajador con UUID: $uuid" }
        return try {
            HibernateManager.transaction {
                val trabajador = manager.find(Trabajador::class.java, uuid)
                trabajador?.let {
                    manager.remove(it)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido eliminar el trabajador con el UUID: $uuid"))
        }
    }

    override suspend fun findAll(): Result<Flow<Trabajador>> {
        logger.debug { "Buscando todos los trabajadores" }
        return try {
            var lista: List<Trabajador> = emptyList()
            HibernateManager.query {
                val query: TypedQuery<Trabajador> =
                    manager.createNamedQuery("Trabajador.findAll", Trabajador::class.java)
                lista = query.resultList
            }
            Result.success(lista.asFlow())
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido obtener la lista de trabajadores"))
        }
    }

    suspend fun deleteAll(): Result<Unit> {
        return try {
            HibernateManager.transaction {
                val query = manager.createQuery("delete from Trabajador")
                query.executeUpdate()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido borrar los trabajadores"))
        }
    }
}
