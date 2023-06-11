package controllers

import exception.CitaControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Cita
import org.litote.kmongo.MongoOperator
import repositories.cita.CitaRepositoryCached
import repositories.cita.ICitaRepository

class CitaController(
    private val citaRepository: ICitaRepository,
    private val cache : CitaRepositoryCached
) {


    suspend fun findAllCita() : Flow<Cita>? {
        return citaRepository.findAll().getOrNull()?.flowOn(Dispatchers.IO)
    }


    suspend fun saveCita(entity: Cita) {
    withContext(Dispatchers.IO) {
        val trabajador = entity.idTrabajador // Obtén el trabajador asignado a la cita

        // Verificar el límite de 4 citas por intervalo para el trabajador
        val citasIntervaloTrabajador = citaRepository.findByTrabajadorAndIntervalo(trabajador, entity.fechaHora)

        if (citasIntervaloTrabajador.getOrDefault(listOf()).size >= 4) {
            throw CitaControllerException("El trabajador no tiene hueco disponible en este intervalo de 30 minutos")
        }

        // Verificar el límite de 8 citas en el mismo intervalo
        val citasIntervalo = citaRepository.findByIntervalo(entity.fechaHora)
        if (citasIntervalo.getOrDefault(listOf()).size >= 8) {
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




    suspend fun findByIdCita(id : String) : Cita?{
        val citaCached = cache.findById(id)
        if(citaCached == null){
            val cita = citaRepository.findById(id)
            if (cita.getOrNull() == null){
                throw CitaControllerException("La cita con el id: $id no existe")
            }else{
                return cita.getOrNull()
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
        }
        cache.delete(id)

    }


    suspend fun updateCita(entity: Cita){
        withContext(Dispatchers.IO){
            launch {
                citaRepository.update(entity)
            }
        }
        cache.update(entity)

    }


}