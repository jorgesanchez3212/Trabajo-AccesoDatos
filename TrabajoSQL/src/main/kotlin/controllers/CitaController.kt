package controllers

import db.HibernateManager
import exception.CitaControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Cita
import repositories.cita.CitaRepositoryCached
import repositories.cita.ICitaRepository
import java.util.UUID

class CitaController(
    private val citaRepository: ICitaRepository,
    private val cache : CitaRepositoryCached
) {


    suspend fun findAllCita() : Flow<Cita>? {
        return citaRepository.findAll().getOrNull()?.flowOn(Dispatchers.IO)
    }


    suspend fun saveCita(entity: Cita) {
        HibernateManager.open()
        withContext(Dispatchers.IO) {
            val trabajador = entity.idTrabajador

            val citasIntervaloTrabajador = citaRepository.findByTrabajadorAndIntervalo(trabajador, entity.fechaHora).getOrDefault(listOf())
            if (citasIntervaloTrabajador.size >= 4) {
                throw CitaControllerException("El trabajador no tiene hueco disponible en este intervalo de 30 minutos")
            }

            val citasIntervalo = citaRepository.findByIntervalo(entity.fechaHora).getOrDefault(listOf())
            if (citasIntervalo.size >= 8) {
                throw CitaControllerException("No hay disponibilidad de citas en este intervalo de 30 minutos")
            }

            launch {
                citaRepository.save(entity)
            }
            launch {
                cache.save(entity)
            }
        }
        HibernateManager.close()
    }






    suspend fun findByIdCita(id : UUID) : Cita?{
        val citaCached = cache.findById(id)
        if(citaCached == null){
            val cita = citaRepository.findById(id).getOrNull()
            if (cita == null){
                throw CitaControllerException("La cita con el id: $id no existe")
            }else{
                return cita
            }
        }else{
            return citaCached
        }

    }

    suspend fun borrarCita(id : UUID){
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
                citaRepository.update(entity)
            }
            launch {
                cache.update(entity)
            }
        }


    }


}