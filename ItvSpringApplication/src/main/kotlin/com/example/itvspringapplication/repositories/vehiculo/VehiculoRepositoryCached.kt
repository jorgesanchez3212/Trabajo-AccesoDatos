package com.example.itvspringapplication.repositories.vehiculo

import com.example.itvspringapplication.models.Vehiculo
import com.example.itvspringapplication.services.cache.vehiculo.VehiculoCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class VehiculoRepositoryCached
    @Autowired constructor(
    private val vehiculosCache: VehiculoCache
)  : IVehiculoRepositoryCached{
    override suspend fun findAll(): Flow<Vehiculo> {
        return vehiculosCache.cache.asMap().values.asFlow()
    }

    override suspend fun findById(id: String): Vehiculo? {
        return vehiculosCache.cache.get(id)
    }

    override suspend fun save(entity: Vehiculo) {
        vehiculosCache.cache.put(entity._id,entity)
    }

    override suspend fun update(entity: Vehiculo) {
        val cacheVehiculo = vehiculosCache.cache.get(entity._id)
        if (cacheVehiculo!=null){
            val vehiculoUpdate = cacheVehiculo.copy(
                marca = entity.marca,
                modelo = entity.modelo,
                matricula = entity.matricula,
                fechaMatriculacion = entity.fechaMatriculacion,
                fechaUltimaRevision = entity.fechaUltimaRevision
            )
            vehiculosCache.cache.put(entity._id,vehiculoUpdate)
        }
    }

    override suspend fun delete(_id: String) {
        vehiculosCache.cache.invalidate(_id)
    }
}