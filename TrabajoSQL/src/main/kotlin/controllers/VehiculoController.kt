package controllers

import exception.VehiculoControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Vehiculo
import repositories.vehiculo.IVehiculoRepository
import repositories.vehiculo.VehiculoRepositoryCached
import java.util.UUID

class VehiculoController(
    private val vehiculoRepository : IVehiculoRepository,
    private val cache : VehiculoRepositoryCached
) {


    suspend fun findAllVehiculo() : Flow<Vehiculo>? {
        return vehiculoRepository.findAll().getOrNull()?.flowOn(Dispatchers.IO)
    }

    suspend fun saveVehiculo(entity : Vehiculo){
        withContext(Dispatchers.IO){
            launch {
                vehiculoRepository.save(entity)
            }
            launch {
                cache.save(entity)
            }
        }

    }


    suspend fun findByIdVehiculo(id : UUID) : Vehiculo?{
        val vehiculoCached = cache.findById(id)
        if(vehiculoCached == null){
            val vehiculo = vehiculoRepository.findById(id).getOrNull()
            if (vehiculo == null){
                throw VehiculoControllerException("EL vehiculo con el id: $id no existe")
            }else{
                return vehiculo
            }
        }else{
            return vehiculoCached
        }
    }

    suspend fun borrarVehiculo(id: UUID){
        withContext(Dispatchers.IO){
            launch {
                vehiculoRepository.delete(id)
            }
            launch {
                cache.delete(id)
            }
        }
    }


    suspend fun updateVehiculo(entity: Vehiculo){
        withContext(Dispatchers.IO) {
            launch {
                vehiculoRepository.update(entity)
            }
            launch {
                cache.update(entity)
            }
        }

    }




}