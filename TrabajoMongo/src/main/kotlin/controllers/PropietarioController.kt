package controllers

import exception.PropietarioControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Propietario
import repositories.propietario.IPropietarioRepository

class PropietarioController(
    private val propietarioRepository: IPropietarioRepository,
    ) {


    suspend fun findAllPropietario() : Flow<Propietario>? {
        return propietarioRepository.findAll().getOrNull()?.flowOn(Dispatchers.IO)
    }

    suspend fun savePropietario(entity : Propietario){
        withContext(Dispatchers.IO){
            launch {
                propietarioRepository.save(entity)
            }
        }
    }


    suspend fun findByIdPropietario(id : String) : Propietario?{
        val propietario = propietarioRepository.findById(id)
        if (propietario.getOrNull() != null){
            return propietario.getOrNull()
        }else{
            throw PropietarioControllerException("No se ha encontrado el propietario con el id $id")
        }
    }

    suspend fun borrarPropietario(id : String){
        withContext(Dispatchers.IO){
            launch {
                propietarioRepository.delete(id)
            }
        }
    }


    suspend fun updatePropietario(entity: Propietario){
        withContext(Dispatchers.IO){
            launch {
                propietarioRepository.update(entity)
            }
        }

    }



}