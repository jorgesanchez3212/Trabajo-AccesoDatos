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
    private val cache : CitaCache
) : ICitaRepositoryCached{


    override suspend fun findAll(): Flow<Cita> {
        val citas = cache.cache.asMap().values.asFlow()
        return citas
    }

    override suspend fun findById(id: String): Cita? {
        val cita = cache.cache.get(id)
        return cita
    }

    override suspend fun save(entity: Cita) {
        cache.cache.put(entity._id,entity)
    }

    override suspend fun update(entity: Cita) {
        val cacheCita = cache.cache.get(entity._id)
        if (cacheCita!=null){
            val citaUpdate = cacheCita.copy(
                fechaHora = entity.fechaHora,
                trabajador = entity.trabajador,
                propietario = entity.propietario,
                vehiculo = entity.vehiculo
            )
            cache.cache.put(entity._id,citaUpdate)
        }

    }

    override suspend fun delete(_id: String) {
        cache.cache.invalidate(_id)
    }
}