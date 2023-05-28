package repositories.cita


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Cita
import services.citas.CitaCache
import java.util.UUID

class CitaRepositoryCached (
    private val cache : CitaCache
) : ICitaRepositoryCached{


    override suspend fun findAll(): Flow<Cita> {
        val citas = cache.cache.asMap().values.asFlow()
        return citas
    }

    override suspend fun findById(id: UUID): Cita? {
        val cita = cache.cache.get(id)
        return cita
    }

    override suspend fun save(entity: Cita) : Cita {
        cache.cache.put(entity.uuid,entity)
        return entity
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

    override suspend fun delete(_id: UUID) {
        cache.cache.invalidate(_id)
    }
}