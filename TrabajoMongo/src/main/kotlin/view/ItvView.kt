package view

import controllers.*
import db.Data
import kotlinx.coroutines.flow.toList
import models.Trabajador
import org.litote.kmongo.newId

class ItvView(
    private val citaController : CitaController,
    private val informeController: InformeController ,
    private val propietarioController: PropietarioController,
    private val trabajadorController: TrabajadorController ,
    private val vehiculoController: VehiculoController
    ) {
    private var logger = KotlinLogging.logger {}


    suspend fun consultas(){
        val trabajadores = trabajadorController.findAllTrabajadores().toList()

        //Trabajador que mas gana sin ser responsable
        val trabajador = trabajadores.filter { it.responsable == false }.maxBy { it.salario }
        println("El trabajador que mas gana sin ser responsable es ${trabajador.toString()}")

        //Salario medio de todos los trabajadores que no son responsables
        val salarioMedio = trabajadores.filter { it.responsable == false }.map { it.salario }.average()
        println("Salario medio de todos los trabajadores que no son responsables es $salarioMedio")

        //El salario medio de todos los trabajadores agrupados por especialidad.
        val salarioMedioAgrupadosPorEspecialidad = trabajadores.groupBy { it.especialidad }.mapValues {
            entry -> entry.value.map { it.salario }.average() }
        println("Salario medio agrupados por especialidad es $salarioMedioAgrupadosPorEspecialidad")

        //- La el trabajador/a con menos antigüedad
        val trabajadorMenosAntiguedad = trabajadores.minBy { it.fechaContratacion }
        println("La el trabajador/a con menos antigüedad $trabajadorMenosAntiguedad")

        // Trabajadores ordenados por especialidad y ordenados por antiguedad
        val trabajadoresOrdenados = trabajadores.sortedWith(compareBy(Trabajador::especialidad,Trabajador::fechaContratacion))
        println("Trabajadores ordenados por especialidad y ordenados por antiguedad son $trabajadoresOrdenados")

    }

    suspend fun  añadirDatos(){
        for (i in Data.trabajadores){
            trabajadorController.saveTrabajador(i)
        }

        for (i in Data.propietarios){
            propietarioController.savePropietario(i)
        }

        for (i in Data.vehiculos){
            vehiculoController.saveVehiculo(i)
        }

        for (i in Data.citas){
            citaController.saveCita(i)
        }

        for (i in Data.informes){
            informeController.saveInforme(i)
        }
    }

    suspend fun comprobarRestricciones(){
        logger.info("Comprobamos las restricciones")
        logger.info("1 -> El email es unico y dos trabajadores no pueden tener el mismo email")
        val trabajador = Trabajador(2,"pepe",6666666,)


    }

}