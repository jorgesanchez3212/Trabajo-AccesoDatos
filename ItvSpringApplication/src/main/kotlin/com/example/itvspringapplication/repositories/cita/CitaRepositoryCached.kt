package com.example.itvspringapplication.repositories.cita

import com.example.itvspringapplication.models.Cita
import com.example.itvspringapplication.services.cache.cita.CitaCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class CitaRepositoryCached
    @Autowired constructor(
    private val citaCache : CitaCache
) : ICitaRepositoryCached{


    override suspend fun findAll(): Flow<Cita> {
        val citas = citaCache.cache.asMap().values.asFlow()
        return citas
    }

    override suspend fun findById(id: String): Cita? {
        val cita = citaCache.cache.get(id)
        return cita
    }

    override suspend fun save(entity: Cita) {
        citaCache.cache.put(entity._id,entity)
    }

    override suspend fun update(entity: Cita) {
        val cacheCita = citaCache.cache.get(entity._id)
        if (cacheCita!=null){
            val citaUpdate = cacheCita.copy(
                fechaHora = entity.fechaHora,
                idTrabajador = entity.idTrabajador,
                idPropietario = entity.idPropietario,
                idVehiculo = entity.idVehiculo
            )
            citaCache.cache.put(entity._id,citaUpdate)
        }

    }

    override suspend fun delete(_id: String) {
        citaCache.cache.invalidate(_id)
    }
}