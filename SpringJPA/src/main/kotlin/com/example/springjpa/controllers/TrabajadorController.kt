package com.example.springjpa.controllers


import com.example.springjpa.exceptions.TrabajadorControllerException
import com.example.springjpa.models.Trabajador
import com.example.springjpa.repositories.trabajador.TrabajadorRepository
import com.example.springjpa.utils.Contraseña
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

@Controller
class TrabajadorController
@Autowired constructor(
    private val trabajadoresRepository : TrabajadorRepository,
) {

    suspend fun findAllTrabajadores() : Flow<Trabajador>{
        return trabajadoresRepository.findAll().asFlow()
    }
    suspend fun saveTrabajador(entity: Trabajador) {
        val trabajador = trabajadoresRepository.findTrabajadorByEmail(entity.email)
        val usernameTrabajador = trabajadoresRepository.findTrabajadorByUsername(entity.username)
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

                    trabajadoresRepository.save(trabajador)

                } else {
                    throw TrabajadorControllerException("El salario está mal calculado")
                }
            }
        }
    }


    suspend fun findByTrabjador(id : UUID) : Trabajador?{

        val trabajador = trabajadoresRepository.findById(id).orElse(null)
        trabajador?.let {
            return it
        }
        throw TrabajadorControllerException("No se ha encontrado el trabajador con id $id")

    }


    suspend fun updateTrabajador(entity: Trabajador){
        trabajadoresRepository.save(entity)

    }


    suspend fun borrarTrabajador(entity: Trabajador){
        trabajadoresRepository.delete(entity)

    }

    suspend fun deleteAll(){
        trabajadoresRepository.deleteAll()
    }
}