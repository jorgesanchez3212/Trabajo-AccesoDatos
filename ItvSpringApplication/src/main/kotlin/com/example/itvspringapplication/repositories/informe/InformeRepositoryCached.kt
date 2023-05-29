package com.example.itvspringapplication.repositories.informe


import com.example.itvspringapplication.models.Informe
import com.example.itvspringapplication.services.cache.informe.InformeCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class InformeRepositoryCached
    @Autowired constructor(
    private val informesCache: InformeCache
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