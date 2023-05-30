package com.example.springjpa.controllers

import com.example.springjpa.exceptions.VehiculoControllerException
import com.example.springjpa.models.Vehiculo
import com.example.springjpa.repositories.vehiculo.VehiculoRepository
import com.example.springjpa.repositories.vehiculo.VehiculoRepositoryCached
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class VehiculoController
@Autowired constructor(
    private val vehiculoRepository : VehiculoRepository,
    private val cache : VehiculoRepositoryCached
) {

    suspend fun findAllVehiculo() : Flow<Vehiculo> {
        return vehiculoRepository.findAll().asFlow()
    }

    suspend fun saveVehiculo(entity : Vehiculo){
        withContext(Dispatchers.IO){
            launch {
                cache.save(entity)
            }
        }
        vehiculoRepository.save(entity)

    }


    suspend fun findByIdVehiculo(id: UUID): Vehiculo {
        val vehiculoCached = cache.findById(id)
        if (vehiculoCached != null) {
            return vehiculoCached
        } else {
            val vehiculo = vehiculoRepository.findById(id).orElse(null)
            vehiculo?.let {
                return it
            }
            throw VehiculoControllerException("El vehiculo con el id: $id no existe")
        }
    }


    suspend fun borrarVehiculo(entity: Vehiculo){
        withContext(Dispatchers.IO){
            launch {
                cache.delete(entity.uuid)
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