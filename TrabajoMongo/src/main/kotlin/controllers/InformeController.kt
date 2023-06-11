package controllers

import exception.InformeControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Informe
import repositories.informe.IInformeRepository
import repositories.informe.InformeRepositoryCached

class InformeController(
    private val informeRepository: IInformeRepository,
    private val cache : InformeRepositoryCached
    ) {

    suspend fun findAllInforme() : Flow<Informe>? {
        return informeRepository.findAll().getOrNull()?.flowOn(Dispatchers.IO)
    }

    suspend fun saveInforme(entity : Informe){
        withContext(Dispatchers.IO){
            launch {
                informeRepository.save(entity)
            }
            launch {
                cache.save(entity)
            }
        }

    }


    suspend fun findByIdInforme(id : String) : Informe?{
        val informeCached = cache.findById(id)
        if(informeCached == null){
            val informe = informeRepository.findById(id)
            if (informe.getOrNull() == null){
                throw InformeControllerException("El informe con el id: $id no existe")
            }else{
                return informe.getOrNull()
            }
        }else{
            return informeCached
        }
    }

    suspend fun borrarInforme(id: String){
        withContext(Dispatchers.IO){
            launch {
                informeRepository.delete(id)
            }
            launch {
                cache.delete(id)
            }
        }
    }


    suspend fun updateInforme(entity: Informe){

        withContext(Dispatchers.IO){
            launch {
                informeRepository.update(entity)            }
            launch {
                cache.update(entity)
            }
        }

    }

}