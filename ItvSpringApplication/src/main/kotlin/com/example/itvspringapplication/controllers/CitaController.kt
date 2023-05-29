package com.example.itvspringapplication.controllers


import com.example.itvspringapplication.exception.CitaControllerException
import com.example.itvspringapplication.models.Cita
import com.example.itvspringapplication.repositories.cita.CitaRepository
import com.example.itvspringapplication.repositories.cita.CitaRepositoryCached
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CitaController(
    private val citaRepository: CitaRepository,
    private val cache : CitaRepositoryCached
) {


    suspend fun findAllCita() : Flow<Cita> {
        return citaRepository.findAll().flowOn(Dispatchers.IO)
    }
/*

    suspend fun saveCita(entity: Cita) {
        withContext(Dispatchers.IO) {
            val trabajador = entity.idTrabajador // Obtén el trabajador asignado a la cita

            // Verificar el límite de 4 citas por intervalo para el trabajador
            val citasIntervaloTrabajador = citaRepository.findByTrabajadorAndIntervalo(trabajador, entity.fechaHora)
            if (citasIntervaloTrabajador.size >= 4) {
                throw CitaControllerException("El trabajador no tiene hueco disponible en este intervalo de 30 minutos")
            }

            // Verificar el límite de 8 citas en el mismo intervalo
            val citasIntervalo = citaRepository.findByIntervalo(entity.fechaHora)
            if (citasIntervalo.size >= 8) {
                throw CitaControllerException("No hay disponibilidad de citas en este intervalo de 30 minutos")
            }

            // Guardar la cita en la base de datos y en el caché
            launch {
                citaRepository.save(entity)
            }
            launch {
                cache.save(entity)
            }
        }
    }



 */


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

    suspend fun borrarCita(entity: Cita ){
        withContext(Dispatchers.IO){
            launch {
                cache.delete(entity._id)
            }
        }
        citaRepository.delete(entity)

    }


    suspend fun updateCita(entity: Cita){
        withContext(Dispatchers.IO){
            launch {
                cache.update(entity)
            }
        }

        citaRepository.save(entity)
    }

    suspend fun deleteAll(){
        citaRepository.deleteAll()
    }


}