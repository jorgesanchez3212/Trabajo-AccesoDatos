package repositories.informe


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Informe
import services.informes.InformeCache
import java.util.UUID

class InformeRepositoryCached(
    private val informesCache: InformeCache
) : IInformeRepositoryCached {
    override suspend fun findAll(): Flow<Informe> {
        return informesCache.cache.asMap().values.asFlow()
    }

    override suspend fun findById(id: UUID): Informe? {
        return informesCache.cache.get(id)
    }

    override suspend fun save(entity: Informe)  {
        informesCache.cache.put(entity.uuid, entity)
    }

    override suspend fun update(entity: Informe) {
        val informe = informesCache.cache.get(entity.uuid)
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

            informesCache.cache.put(entity.uuid,informeUpdated)
        }
    }

    override suspend fun delete(_id: UUID) {
        informesCache.cache.invalidate(_id)
    }
}