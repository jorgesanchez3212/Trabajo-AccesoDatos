package com.example.springjpa.controllers

import com.example.springjpa.exceptions.InformeControllerException
import com.example.springjpa.models.Informe
import com.example.springjpa.repositories.informe.InformeRepository
import com.example.springjpa.repositories.informe.InformeRepositoryCached
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class InformeController
@Autowired constructor(
    private val informeRepository: InformeRepository,
    private val cache : InformeRepositoryCached
) {

    suspend fun findAllInforme() : Flow<Informe> {
        return informeRepository.findAll().asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun saveInforme(entity : Informe){
        withContext(Dispatchers.IO){
            launch {
                cache.save(entity)
            }
        }
        informeRepository.save(entity)

    }


    suspend fun findByIdInforme(id : UUID) : Informe?{
        val informeCached = cache.findById(id)
        if(informeCached == null){
            val informe = informeRepository.findById(id).orElse(null)
            informe?.let {
                return it
            }
            throw InformeControllerException("El informe con el id: $id no existe")

        }else{
            return informeCached
        }
    }

    suspend fun borrarInforme(entity: Informe){
        withContext(Dispatchers.IO){
            launch {
                cache.delete(entity.uuid)
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