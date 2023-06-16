package com.example.itvspringapplication.controllers


import com.example.itvspringapplication.exception.CitaControllerException
import com.example.itvspringapplication.models.Cita
import com.example.itvspringapplication.repositories.cita.CitaRepository
import com.example.itvspringapplication.repositories.cita.CitaRepositoryCached
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class CitaController
    @Autowired constructor(
    private val citaRepository: CitaRepository,
    private val cache : CitaRepositoryCached
) {

    private val _state = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val state = _state.asSharedFlow()

    suspend fun findAllCita() : Flow<Cita> {
        return citaRepository.findAll().flowOn(Dispatchers.IO)
    }


    suspend fun saveCita(entity: Cita) {

        println("Estamos en el metodo save")

            val trabajador = entity.idTrabajador // Obtenemos el trabajador asignado a la cita

            val intervaloFin = entity.fechaHora.plusMinutes(30)
            // Verificamos el límite de 4 citas por intervalo para el trabajador
            val citas = citaRepository.findAll()
            val citasIntervaloTrabjador = citas.filter { cita ->
            cita.idTrabajador == trabajador && cita.fechaHora.toString() in entity.fechaHora.toString()..intervaloFin.toString()
            }.toList()
            if (citasIntervaloTrabjador.size >= 4) {
                throw CitaControllerException("El trabajador no tiene hueco disponible en este intervalo de 30 minutos")
            }

            // Verificamos el límite de 8 citas en el mismo intervalo
            val citasIntervalo = citas.filter { cita -> cita.fechaHora in entity.fechaHora..intervaloFin }.toList()
            if (citasIntervalo.size >= 8) {
                throw CitaControllerException("No hay disponibilidad de citas en este intervalo de 30 minutos")
            }




        println("Vamos a añadir a la cache")
        withContext(Dispatchers.IO) {
            launch {
                cache.save(entity)
                _state.emit("Se ha añadido la cita $entity")

            }
        }

        println("Vamos a añadir a la base de datos")
        citaRepository.save(entity)

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

    suspend fun borrarCita(entity: Cita ){
        withContext(Dispatchers.IO){
            launch {
                cache.delete(entity._id)
            }
        }
        citaRepository.delete(entity)
        _state.emit("Se ha borrado la cita $entity")


    }


    suspend fun updateCita(entity: Cita){
        withContext(Dispatchers.IO){
            launch {
                cache.update(entity)
            }
        }

        citaRepository.save(entity)
        _state.emit("Se ha actualizado la cita $entity")

    }

    suspend fun deleteAll(){
        citaRepository.deleteAll()
    }


}