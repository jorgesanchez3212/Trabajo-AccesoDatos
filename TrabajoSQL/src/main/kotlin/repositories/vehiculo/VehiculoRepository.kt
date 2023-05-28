package repositories.vehiculo

import db.HibernateManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Vehiculo
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

private var logger = KotlinLogging.logger {  }

class VehiculoRepository : IVehiculoRepository {
    override suspend fun findAll(): Flow<Vehiculo> {
        logger.info { "Buscando todos los vehiculos" }
        var lista = mutableListOf<Vehiculo>()
        HibernateManager.query {
            var query : TypedQuery<Vehiculo> = HibernateManager.manager.createNamedQuery("Vehiculo.findAll",
                Vehiculo::class.java)
            lista = query.resultList
        }
        return lista.asFlow()
    }

    override suspend fun findById(id: UUID): Vehiculo? {
        logger.info { "Buscando el vehiculo con id $id" }
        var encontrado : Vehiculo? = null
        HibernateManager.query {
            encontrado = HibernateManager.manager.find(Vehiculo::class.java, id)
        }
        return encontrado
    }

    override suspend fun save(entity: Vehiculo) {
        logger.info { "Insertando el vehiculo $entity" }
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
    }

    override suspend fun update(entity: Vehiculo) {
        logger.info { "Actualizar el vehiculo $entity"}
        HibernateManager.transaction {
            var vehiculoExisting = HibernateManager.manager.find(Vehiculo::class.java,entity.uuid)
            if (vehiculoExisting != null){
                vehiculoExisting.marca = entity.marca
                vehiculoExisting.modelo = entity.modelo
                vehiculoExisting.matricula = entity.matricula
                vehiculoExisting.fechaMatriculacion = entity.fechaMatriculacion
                vehiculoExisting.fechaUltimaRevision = entity.fechaUltimaRevision


                HibernateManager.manager.merge(vehiculoExisting)
                logger.info { "Vehiculo actualizado correctamente" }
            }else{
                logger.info { "Vehiculo no encontrado" }

            }
        }
    }

    override suspend fun delete(_id: UUID) {
        logger.info { "Eliminando vehiculo" }
        var vehiculo : Vehiculo? = null
        HibernateManager.transaction {
            vehiculo = HibernateManager.manager.find(Vehiculo::class.java,_id)
            if (vehiculo != null){
                HibernateManager.manager.remove(vehiculo)
                logger.info { "Vehiculo eliminado correctamente" }
            }else{
                logger.info { "Vehiculo no encontrado" }
            }

        }
    }
}