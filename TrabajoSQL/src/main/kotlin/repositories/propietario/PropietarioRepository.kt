package repositories.propietario

import db.HibernateManager
import db.HibernateManager.manager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Propietario
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

private var logger = KotlinLogging.logger {  }

class PropietarioRepository : IPropietarioRepository {
    override suspend fun findAll(): Flow<Propietario> {
        logger.info { "Buscando todos los propietarios" }
        var lista = mutableListOf<Propietario>()
        HibernateManager.query {
            var query : TypedQuery<Propietario> = manager.createNamedQuery("Propietario.findAll",Propietario::class.java)
            lista = query.resultList
        }
        return lista.asFlow()
    }

    override suspend fun findById(id: UUID): Propietario? {
        logger.info { "Buscando el propietario con id $id" }
        var encontrado : Propietario? = null
        HibernateManager.query {
            encontrado = manager.find(Propietario::class.java, id)
        }
        return encontrado

    }

    override suspend fun save(entity: Propietario) : Propietario {
        logger.info { "Insertando el propietario $entity" }
        var propietario : Propietario? = null
        HibernateManager.transaction {
            propietario = manager.merge(entity)
        }
        return propietario!!
    }

    override suspend fun update(entity: Propietario) {
        logger.info { "Actualizar el propietario $entity"}
        HibernateManager.transaction {
            var propietarioExisting = manager.find(Propietario::class.java,entity.uuid)
            if (propietarioExisting != null){
                propietarioExisting.dni = entity.nombre
                propietarioExisting.nombre = entity.nombre
                propietarioExisting.apellidos = entity.apellidos
                propietarioExisting.teléfono = entity.teléfono

                manager.merge(propietarioExisting)
                logger.info { "Propietario actualizado correctamente" }
            }else{
                logger.info { "Propietario no encontrado" }

            }
        }

    }

    override suspend fun delete(_id: UUID) {
        logger.info { "Eliminando propietario" }
        var propietario : Propietario? = null
        HibernateManager.transaction {
            propietario = manager.find(Propietario::class.java,_id)
            if (propietario != null){
                manager.remove(propietario)
                logger.info { "Propietario eliminado correctamente" }
            }else{
                logger.info { "Propietario no encontrado" }
            }

        }
    }

    fun deleteAll(): Boolean {
        var eliminado = false
        HibernateManager.transaction {
            var query = manager.createQuery("delete from Propietario ")
            query.executeUpdate()
            eliminado = true
        }
        return eliminado
    }
}