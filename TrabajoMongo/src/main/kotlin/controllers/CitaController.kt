package controllers

import exception.CitaControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Cita
import repositories.cita.CitaRepositoryCached
import repositories.cita.ICitaRepository

class CitaController(
    private val citaRepository: ICitaRepository,
    private val cache : CitaRepositoryCached
) {


    suspend fun findAllCita() : Flow<Cita> {
        return citaRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun saveCita(entity : Cita){
        withContext(Dispatchers.IO){
            launch {
                citaRepository.save(entity)
            }
            launch {
                cache.save(entity)
            }
        }
    }


    suspend fun findByIdCita(id : String) : Cita?{
        val citaCached = cache.findById(id)
        if(citaCached == null){
            val cita = citaRepository.findById(id)
            if (cita == null){
                throw CitaControllerException("La cita con el id: $id no existe")
            }else{
                return cita
            }
        }else{
            return citaCached
        }

    }

    suspend fun borrarCita(id : String){
        withContext(Dispatchers.IO){
            launch {
                citaRepository.delete(id)
            }
            launch {
                cache.delete(id)
            }
        }
    }


    suspend fun updateCita(entity: Cita){
        withContext(Dispatchers.IO){
            launch {
                citaRepository.update(entity)            }
            launch {
                cache.update(entity)
            }
        }


    }


}