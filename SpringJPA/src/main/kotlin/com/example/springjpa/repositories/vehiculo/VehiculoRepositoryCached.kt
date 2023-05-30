package com.example.springjpa.repositories.vehiculo


import com.example.springjpa.models.Vehiculo
import com.example.springjpa.services.VehiculoCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import java.util.UUID
@Repository
class VehiculoRepositoryCached
    @Autowired constructor(
    private val vehiculosCache: VehiculoCache
)  : IVehiculoRepositoryCached{
    override suspend fun findAll(): Flow<Vehiculo> {
        return vehiculosCache.cache.asMap().values.asFlow()
    }

    override suspend fun findById(id: UUID): Vehiculo? {
        return vehiculosCache.cache.get(id)
    }

    override suspend fun save(entity: Vehiculo) {
        vehiculosCache.cache.put(entity.uuid,entity)
    }

    override suspend fun update(entity: Vehiculo) {
        val cacheVehiculo = vehiculosCache.cache.get(entity.uuid)
        if (cacheVehiculo!=null){
            val vehiculoUpdate = cacheVehiculo.copy(
                marca = entity.marca,
                modelo = entity.modelo,
                matricula = entity.matricula,
                fechaMatriculacion = entity.fechaMatriculacion,
                fechaUltimaRevision = entity.fechaUltimaRevision
            )
            vehiculosCache.cache.put(entity.uuid,vehiculoUpdate)
        }
    }

    override suspend fun delete(_id: UUID) {
        vehiculosCache.cache.invalidate(_id)
    }
}