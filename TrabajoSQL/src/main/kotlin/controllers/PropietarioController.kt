package controllers

import exception.PropietarioControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Propietario
import repositories.propietario.IPropietarioRepository
import java.util.UUID

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


    suspend fun findByIdPropietario(id : UUID) : Propietario?{
        val propietario = propietarioRepository.findById(id).getOrNull()
        if (propietario!= null){
            return propietario
        }else{
            throw PropietarioControllerException("No se ha encontrado el propietario con el id $id")
        }
    }

    suspend fun borrarPropietario(id : UUID){
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