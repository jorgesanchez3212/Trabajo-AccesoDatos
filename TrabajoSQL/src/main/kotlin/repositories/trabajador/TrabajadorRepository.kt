package repositories.trabajador

import db.HibernateManager
import db.HibernateManager.manager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Trabajador
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

class TrabajadorRepository : ITrabajadorRepository {
    private var logger = KotlinLogging.logger {}



    override suspend fun findByEmail(email: String): Trabajador? {
        logger.debug { "Buscando trabajador por su email" }
        var encontrado:Trabajador? = null
        HibernateManager.query {
            var query: TypedQuery<Trabajador> = manager.createNamedQuery("Trabajador.findByEmail",  Trabajador::class.java)
            query.setParameter("email", email)
            encontrado = query.resultList.firstOrNull()
        }
        return encontrado
    }

    override suspend fun findByUsername(username: String): Trabajador? {
        logger.debug { "Buscando trabajador por su username" }
        var encontrado:Trabajador? = null
        HibernateManager.query {
            var query: TypedQuery<Trabajador> = manager.createNamedQuery("Trabajador.findByUsername",  Trabajador::class.java)
            query.setParameter("username", username)
            encontrado = query.resultList.firstOrNull()
        }
        return encontrado
    }

    override suspend fun findById(id: UUID): Trabajador? {
        logger.debug { "Buscando trabajador por el id" }
        var encontrado: Trabajador? = null
        HibernateManager.query {
            encontrado = manager.find(Trabajador::class.java, id)
        }
        return encontrado
    }

    override suspend fun save(item: Trabajador) {
        logger.debug { "Insertando trabajador" }
        var add: Trabajador? = null
        HibernateManager.transaction {
            add = manager.merge(item)
        }
    }

    override suspend fun update(entity: Trabajador) {
        logger.debug { "Actualizando trabajador con UUID: ${entity.uuid}" }
        HibernateManager.transaction {
            var existingTrabajador = manager.find(Trabajador::class.java, entity.uuid)
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
            } else {
                logger.debug { "No se encontró ningún trabajador con el UUID: ${entity.uuid}. No se realizó ninguna actualización." }
            }
        }
    }


    override suspend fun delete(uuid: UUID) {
        logger.debug { "Eliminando trabajador"}
        HibernateManager.transaction {
            val trabajador = manager.find(Trabajador::class.java, uuid)
            if (trabajador != null) {
                manager.remove(trabajador)
                logger.debug { "Trabajador eliminado correctamente" }
            } else {
                logger.debug { "No se encontró ningún trabajador con el UUID: $uuid" }
            }
        }

    }


    override suspend fun findAll(): Flow<Trabajador> {
        logger.debug { "Buscando todos los trabajadores" }
        var lista = mutableListOf<Trabajador>()
        HibernateManager.query {
            var query: TypedQuery<Trabajador> = manager.createNamedQuery("Trabajador.findAll", Trabajador::class.java)
            lista = query.resultList
        }
        return lista.asFlow()
    }

}