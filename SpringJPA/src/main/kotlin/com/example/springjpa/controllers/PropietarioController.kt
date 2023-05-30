package com.example.springjpa.controllers

import com.example.springjpa.exceptions.PropietarioControllerException
import com.example.springjpa.models.Propietario
import com.example.springjpa.repositories.propietario.PropietarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class PropietarioController
@Autowired constructor(
    private val propietarioRepository: PropietarioRepository,
) {


    suspend fun findAllPropietario() : Flow<Propietario> {
        return propietarioRepository.findAll().asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun savePropietario(entity : Propietario){
        propietarioRepository.save(entity)

    }


    suspend fun findByIdPropietario(id : UUID) : Propietario?{
        val propietario = propietarioRepository.findById(id).orElse(null)
        propietario?.let {
            return it
        }
        throw PropietarioControllerException("No se ha encontrado el propietario con el id $id")
    }

    suspend fun borrarPropietario(entity : Propietario){
        propietarioRepository.delete(entity)
    }


    suspend fun updatePropietario(entity: Propietario){
        propietarioRepository.save(entity)

    }

    suspend fun deleteAll(){
        propietarioRepository.deleteAll()
    }

}