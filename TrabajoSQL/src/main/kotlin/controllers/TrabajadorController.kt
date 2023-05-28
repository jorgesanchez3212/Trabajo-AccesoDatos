package controllers

import exception.TrabajadorControllerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.*
import repositories.trabajador.ITrabajadorRepository
import utils.cifrado.Contraseña
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

class TrabajadorController(
    private val trabajadoresRepository : ITrabajadorRepository,
) {


    suspend fun findAllTrabajadores() : Flow<Trabajador>{
        return trabajadoresRepository.findAll().flowOn(Dispatchers.IO)
    }
    suspend fun saveTrabajador(entity: Trabajador) {
        val trabajador = trabajadoresRepository.findByEmail(entity.email)
        val usernameTrabajador = trabajadoresRepository.findByUsername(entity.username)
        var salarioTrabajador: Int = 0

        val años = ChronoUnit.YEARS.between(entity.fechaContratacion, LocalDate.now())
        val incrementoPorAntiguedad = (años.toInt() / 3) * 100
        salarioTrabajador += incrementoPorAntiguedad

        when (entity.especialidad) {
            Trabajador.Especialidad.MECANICA.name -> salarioTrabajador += 1600
            Trabajador.Especialidad.MOTOR.name -> salarioTrabajador += 1700
            Trabajador.Especialidad.ELECTRICIDAD.name -> salarioTrabajador += 1800
            Trabajador.Especialidad.INTERIOR.name -> salarioTrabajador += 1750
            else -> throw TrabajadorControllerException("La especialidad es incorrecta")
        }

        if (entity.responsable == true) {
            salarioTrabajador += 1000
        }

        if (trabajador != null) {
            throw TrabajadorControllerException("El email ${entity.email} ya existe")
        } else {
            if (usernameTrabajador != null) {
                throw TrabajadorControllerException("El username ${entity.username} ya existe")
            } else {
                if (entity.salario == salarioTrabajador) {
                    val trabajador = Trabajador(
                        entity.uuid,
                        entity.nombre,
                        entity.telefono,
                        entity.email,
                        entity.username,
                        Contraseña.encriptarContraseña(entity.contraseña.toString()),
                        entity.fechaContratacion,
                        entity.especialidad,
                        entity.salario,
                        entity.responsable
                    )
                    withContext(Dispatchers.IO) {
                        launch {
                            trabajadoresRepository.save(trabajador)
                        }
                    }
                } else {
                    throw TrabajadorControllerException("El salario está mal calculado")
                }
            }
        }
    }




    suspend fun updateTrabajador(entity: Trabajador){
        withContext(Dispatchers.IO){
            launch {
                trabajadoresRepository.update(entity)
            }
        }

    }


    suspend fun borrarTrabajador(id: UUID){
        withContext(Dispatchers.IO){
            launch {
                trabajadoresRepository.delete(id)
            }
        }
    }



}