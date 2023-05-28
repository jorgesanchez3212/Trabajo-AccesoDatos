package repositories.informe

import db.HibernateManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Informe
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

private var logger = KotlinLogging.logger {  }

class InformeRepository : IInformeRepository {
    override suspend fun findAll(): Flow<Informe> {
        logger.info { "Buscando todos los informes" }
        var lista = mutableListOf<Informe>()
        HibernateManager.query {
            var query : TypedQuery<Informe> = HibernateManager.manager.createNamedQuery("Informe.findAll",
                Informe::class.java)
            lista = query.resultList
        }
        return lista.asFlow()
    }

    override suspend fun findById(id: UUID): Informe? {
        logger.info { "Buscando el informe con id $id" }
        var encontrado : Informe? = null
        HibernateManager.query {
            encontrado = HibernateManager.manager.find(Informe::class.java, id)
        }
        return encontrado
    }

    override suspend fun save(entity: Informe) {
        logger.info { "Insertando el vehiculo $entity" }
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
    }

    override suspend fun update(entity: Informe) {
        logger.info { "Actualizar el informe $entity"}
        HibernateManager.transaction {
            var existing = HibernateManager.manager.find(Informe::class.java,entity.uuid)
            if (existing != null){
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
            }else{
                logger.info { "Informe no encontrado" }

            }
        }
    }

    override suspend fun delete(_id: UUID) {
        logger.info { "Eliminando informe" }
        var informe : Informe? = null
        HibernateManager.transaction {
            informe = HibernateManager.manager.find(Informe::class.java,_id)
            if (informe != null){
                HibernateManager.manager.remove(informe)
                logger.info { "Informe eliminado correctamente" }
            }else{
                logger.info { "Informe no encontrado" }
            }

        }
    }
}