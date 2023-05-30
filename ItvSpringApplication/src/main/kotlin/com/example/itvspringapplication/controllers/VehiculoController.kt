package com.example.itvspringapplication.controllers


import com.example.itvspringapplication.exception.VehiculoControllerException
import com.example.itvspringapplication.models.Vehiculo
import com.example.itvspringapplication.repositories.vehiculo.VehiculoRepository
import com.example.itvspringapplication.repositories.vehiculo.VehiculoRepositoryCached
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class VehiculoController
    @Autowired constructor(
    private val vehiculoRepository : VehiculoRepository,
    private val cache : VehiculoRepositoryCached
) {

    suspend fun findAllVehiculo() : Flow<Vehiculo> {
        return vehiculoRepository.findAll()
    }

    suspend fun saveVehiculo(entity : Vehiculo){
        withContext(Dispatchers.IO){
            launch {
                cache.save(entity)
            }
        }
        vehiculoRepository.save(entity)

    }


    suspend fun findByIdVehiculo(id : String) : Vehiculo?{
        val vehiculoCached = cache.findById(id)
        if(vehiculoCached == null){
            val vehiculo = vehiculoRepository.findById(id)
            if (vehiculo == null){
                throw VehiculoControllerException("EL vehiculo con el id: $id no existe")
            }else{
                return vehiculo
            }
        }else{
            return vehiculoCached
        }
    }

    suspend fun borrarVehiculo(entity: Vehiculo){
        withContext(Dispatchers.IO){
            launch {
                cache.delete(entity._id)
            }
        }
        vehiculoRepository.delete(entity)
    }


    suspend fun updateVehiculo(entity: Vehiculo){
        withContext(Dispatchers.IO) {
            launch {
                cache.update(entity)
            }
        }
        vehiculoRepository.save(entity)

    }




}