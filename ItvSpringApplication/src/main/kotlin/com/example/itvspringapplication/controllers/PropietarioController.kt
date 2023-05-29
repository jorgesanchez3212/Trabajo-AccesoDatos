package com.example.itvspringapplication.controllers
import com.example.itvspringapplication.exception.PropietarioControllerException
import com.example.itvspringapplication.models.Propietario
import com.example.itvspringapplication.models.Trabajador
import com.example.itvspringapplication.repositories.propietario.PropietarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class PropietarioController
    @Autowired constructor(
    private val propietarioRepository: PropietarioRepository,
) {


    suspend fun findAllPropietario() : Flow<Propietario> {
        return propietarioRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun savePropietario(entity : Propietario){
        propietarioRepository.save(entity)

    }


    suspend fun findByIdPropietario(id : String) : Propietario?{
        val propietario = propietarioRepository.findById(id)
        if (propietario!= null){
            return propietario
        }else{
            throw PropietarioControllerException("No se ha encontrado el propietario con el id $id")
        }
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