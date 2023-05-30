package com.example.springjpa.controllers

import com.example.springjpa.exceptions.CitaControllerException
import com.example.springjpa.models.Cita
import com.example.springjpa.repositories.cita.CitaRepository
import com.example.springjpa.repositories.cita.CitaRepositoryCached
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
class CitaController
@Autowired constructor(
    private val citaRepository: CitaRepository,
    private val cache : CitaRepositoryCached
) {


    suspend fun findAllCita() : Flow<Cita> {
        return citaRepository.findAll().asFlow().flowOn(Dispatchers.IO)
    }


    suspend fun saveCita(entity: Cita) {
        withContext(Dispatchers.IO) {
            val trabajador = entity.trabajador // Obtén el trabajador asignado a la cita

            val intervaloFin = entity.fechaHora.plusMinutes(30)
            // Verificar el límite de 4 citas por intervalo para el trabajador
            val citasIntervaloTrabajador = citaRepository.findCitasByTrabajadorAndFechaHoraBetween(trabajador, entity.fechaHora,intervaloFin)
            if (citasIntervaloTrabajador.size >= 4) {
                throw CitaControllerException("El trabajador no tiene hueco disponible en este intervalo de 30 minutos")
            }

            // Verificar el límite de 8 citas en el mismo intervalo
            val citasIntervalo = citaRepository.findCitasByFechaHoraBetween(entity.fechaHora,intervaloFin)
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






    suspend fun findByIdCita(id : UUID) : Cita?{
        val citaCached = cache.findById(id)
        if(citaCached == null){
            val cita = citaRepository.findById(id).orElse(null)
            cita?.let {
                return it
            }
            throw CitaControllerException("La cita con el id: $id no existe")
        }else{
            return citaCached
        }

    }

    suspend fun borrarCita(entity: Cita ){
        withContext(Dispatchers.IO){
            launch {
                cache.delete(entity.uuid)
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