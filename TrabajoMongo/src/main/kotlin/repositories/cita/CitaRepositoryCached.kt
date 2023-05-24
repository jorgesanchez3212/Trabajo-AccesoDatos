package repositories.cita

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Cita
import services.citas.CitaCache

class CitaRepositoryCached (
    private val cache : CitaCache
        ) : ICitaRepository{
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
                idTrabajador = entity.idTrabajador,
                idPropietario = entity.idPropietario,
                idVehiculo = entity.idVehiculo
            )
            cache.cache.put(entity._id,citaUpdate)
        }

    }

    override suspend fun delete(_id: String) {
        cache.cache.invalidate(_id)
    }
}