package repositories.vehiculo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Vehiculo
import services.vehiculos.VehiculosCache
import java.util.UUID

class VehiculoRepositoryCached(
    private val vehiculosCache: VehiculosCache
)  : IVehiculoRepository{
    override suspend fun findAll(): Flow<Vehiculo> {
        return vehiculosCache.cache.asMap().values.asFlow()
    }

    override suspend fun findById(id: UUID): Vehiculo? {
        return vehiculosCache.cache.get(id)
    }

    override suspend fun save(entity: Vehiculo) : Vehiculo {
        vehiculosCache.cache.put(entity.uuid,entity)
        return entity
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