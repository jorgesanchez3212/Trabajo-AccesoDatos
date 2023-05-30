package com.example.itvspringapplication.controllers


import com.example.itvspringapplication.exception.TrabajadorControllerException
import com.example.itvspringapplication.models.Trabajador
import com.example.itvspringapplication.repositories.trabajador.TrabajadorRepository
import com.example.itvspringapplication.utils.Cifrado
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

import java.time.LocalDate
import java.time.temporal.ChronoUnit
@Controller
class TrabajadorController
    @Autowired constructor(
        private val trabajadoresRepository : TrabajadorRepository,
) {

    suspend fun findAllTrabajadores() : Flow<Trabajador>{
        return trabajadoresRepository.findAll()
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
                        entity._id,
                        entity.nombre,
                        entity.teléfono,
                        entity.email,
                        entity.username,
                        Cifrado.encriptarContraseña(entity.contraseña.toString()),
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


    suspend fun findByTrabjador(id : String) : Trabajador?{

        val trabajador = trabajadoresRepository.findById(id)
        if (trabajador == null){
            throw TrabajadorControllerException("EL trabajador con el id: $id no existe")
        }else{
            return trabajador
        }

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