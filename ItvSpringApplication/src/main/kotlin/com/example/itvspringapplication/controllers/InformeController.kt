package com.example.itvspringapplication.controllers

import com.example.itvspringapplication.exception.InformeControllerException
import com.example.itvspringapplication.models.Informe
import com.example.itvspringapplication.repositories.informe.InformeRepository
import com.example.itvspringapplication.repositories.informe.InformeRepositoryCached
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class InformeController
    @Autowired constructor(
    private val informeRepository: InformeRepository,
    private val cache : InformeRepositoryCached
) {

    suspend fun findAllInforme() : Flow<Informe> {
        return informeRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun saveInforme(entity : Informe){
        withContext(Dispatchers.IO){
            launch {
                cache.save(entity)
            }
        }
        informeRepository.save(entity)

    }


    suspend fun findByIdInforme(id : String) : Informe?{
        val informeCached = cache.findById(id)
        if(informeCached == null){
            val informe = informeRepository.findById(id)
            if (informe == null){
                throw InformeControllerException("El informe con el id: $id no existe")
            }else{
                return informe
            }
        }else{
            return informeCached
        }
    }

    suspend fun borrarInforme(entity: Informe){
        withContext(Dispatchers.IO){
            launch {
                cache.delete(entity._id)
            }
        }
        informeRepository.delete(entity)

    }


    suspend fun updateInforme(entity: Informe){
        withContext(Dispatchers.IO){
            launch {
                cache.update(entity)
            }
        }
            informeRepository.save(entity)

    }

    suspend fun deleteAll(){
        informeRepository.deleteAll()
    }

}