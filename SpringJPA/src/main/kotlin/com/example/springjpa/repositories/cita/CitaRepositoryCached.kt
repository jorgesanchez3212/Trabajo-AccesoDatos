package com.example.springjpa.repositories.cita

import com.example.springjpa.models.Cita
import com.example.springjpa.services.CitaCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CitaRepositoryCached
@Autowired constructor(
    private val cache : CitaCache
) : ICitaRepositoryCached{


    override suspend fun findAll(): Flow<Cita> {
        val citas = cache.cache.asMap().values.asFlow()
        return citas
    }

    override suspend fun findById(uuid: UUID): Cita? {
        val cita = cache.cache.get(uuid)
        return cita
    }

    override suspend fun save(entity: Cita) {
        cache.cache.put(entity.uuid,entity)
    }

    override suspend fun update(entity: Cita) {
        val cacheCita = cache.cache.get(entity.uuid)
        if (cacheCita!=null){
            val citaUpdate = cacheCita.copy(
                fechaHora = entity.fechaHora,
                idTrabajador = entity.idTrabajador,
                idPropietario = entity.idPropietario,
                idVehiculo = entity.idVehiculo
            )
            cache.cache.put(entity.uuid,citaUpdate)
        }

    }

    override suspend fun delete(uuid: UUID) {
        cache.cache.invalidate(uuid)
    }
}