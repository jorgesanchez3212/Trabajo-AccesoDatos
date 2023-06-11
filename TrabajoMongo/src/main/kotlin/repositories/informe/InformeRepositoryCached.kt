package repositories.informe

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Informe
import services.informes.InformesCache

class InformeRepositoryCached(
    private val informesCache: InformesCache
) : IInformeRepositoryCached {
    override suspend fun findAll(): Flow<Informe> {
       return informesCache.cache.asMap().values.asFlow()
    }

    override suspend fun findById(id: String): Informe? {
        return informesCache.cache.get(id)
    }

    override suspend fun save(entity: Informe) {
        informesCache.cache.put(entity._id, entity)
    }

    override suspend fun update(entity: Informe) {
        val informe = informesCache.cache.get(entity._id)
        if (informe != null){
            val informeUpdated = informe.copy(
                frenado = entity.frenado,
                contaminación = entity.contaminación,
                aptoFrenado = entity.aptoFrenado,
                luces = entity.luces,
                apto = entity.apto,
                idTrabajador = entity.idTrabajador,
                idVehiculo = entity.idVehiculo,
                idPropietario = entity.idPropietario
            )

            informesCache.cache.put(entity._id,informeUpdated)
        }
    }

    override suspend fun delete(_id: String) {
        informesCache.cache.invalidate(_id)
    }
}