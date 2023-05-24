package repositories.vehiculo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Vehiculo
import services.vehiculos.VehiculosCache

class VehiculoRepositoryCached(
    private val vehiculosCache: VehiculosCache
)  : IVehiculoRepository{
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