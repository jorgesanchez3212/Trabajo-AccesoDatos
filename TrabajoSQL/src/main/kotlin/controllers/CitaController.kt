package controllers

import db.HibernateManager
import exception.CitaControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
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

    private val _state = MutableSharedFlow<Cita>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val state = _state.asSharedFlow()

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
                _state.emit(entity)
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
                _state.emit(entity)

            }
            launch {
                cache.update(entity)
            }
        }


    }


}