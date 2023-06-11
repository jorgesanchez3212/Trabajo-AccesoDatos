package view

import controllers.*
import db.Data
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Trabajador
import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import kotlin.io.path.Path

class ItvView(
    private val citaController : CitaController,
    private val informeController: InformeController ,
    private val propietarioController: PropietarioController,
    private val trabajadorController: TrabajadorController ,
    private val vehiculoController: VehiculoController
    ) {
    private var logger = KotlinLogging.logger {}


    suspend fun informes(ruta: String){
        val lista = leerCSV(ruta)
        lista.forEach {
            trabajadorController.saveTrabajador(it)
        }
        val trabajadores = trabajadorController.findAllTrabajadores().toList()
        val list = mutableListOf<String>()

        //Trabajador que mas gana sin ser responsable
        val trabajador = trabajadores.filter { it.responsable == false }.maxBy { it.salario }
        println("El trabajador que mas gana sin ser responsable es ${trabajador.toString()}")
        list.add("El trabajador que mas gana sin ser responsable es ${trabajador.toString()}")

        //Salario medio de todos los trabajadores que no son responsables
        val salarioMedio = trabajadores.filter { it.responsable == false }.map { it.salario }.average()
        println("Salario medio de todos los trabajadores que no son responsables es $salarioMedio")
        list.add("Salario medio de todos los trabajadores que no son responsables es $salarioMedio")

        //El salario medio de todos los trabajadores agrupados por especialidad.
        val salarioMedioAgrupadosPorEspecialidad = trabajadores.groupBy { it.especialidad }.mapValues {
            entry -> entry.value.map { it.salario }.average() }
        println("Salario medio agrupados por especialidad es $salarioMedioAgrupadosPorEspecialidad")
        list.add("Salario medio agrupados por especialidad es $salarioMedioAgrupadosPorEspecialidad")

        //- La el trabajador/a con menos antigüedad
        val trabajadorMenosAntiguedad = trabajadores.minBy { it.fechaContratacion }
        println("La el trabajador/a con menos antigüedad $trabajadorMenosAntiguedad")
        list.add("La el trabajador/a con menos antigüedad $trabajadorMenosAntiguedad")

        // Trabajadores ordenados por especialidad y ordenados por antiguedad
        val trabajadoresOrdenados = trabajadores.sortedWith(compareBy(Trabajador::especialidad,Trabajador::fechaContratacion))
        println("Trabajadores ordenados por especialidad y ordenados por antiguedad son $trabajadoresOrdenados")
        list.add("Trabajadores ordenados por especialidad y ordenados por antiguedad son $trabajadoresOrdenados")

        exportarJSON("./metadata",list)
        exportarJSONTrabajadores("./metadata", lista)

        lista.forEach {
            trabajadorController.borrarTrabajador(it._id)
        }

    }

    fun exportarJSON(ruta: String, contenedores: List<String>) {
        logger.debug { "Exportando archivo json" }
        if (Files.exists(Path(ruta))) {
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "consultas.json")
            fichero.writeText(json.encodeToString(contenedores))
        }else {
            Files.createDirectories(Path(ruta))
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "consultas.json")
            fichero.writeText(json.encodeToString(contenedores))
        }

    }

    fun exportarJSONTrabajadores(ruta: String, trabajadores: List<Trabajador>) {
        logger.debug { "Exportando archivo json" }
        if (Files.exists(Path(ruta))) {
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "trabajadores.json")
            fichero.writeText(json.encodeToString(trabajadores))
        }else {
            Files.createDirectories(Path(ruta))
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "trabajadores.json")
            fichero.writeText(json.encodeToString(trabajadores))
        }

    }



    fun leerCSV(ruta: String) : List<Trabajador>{
        logger.debug{"Leyendo archivo csv"}
            val fichero = File(ruta)
            if(fichero.exists()&&ruta.endsWith(".csv")) {
                if(fichero.readLines().take(1).first().split(";").size == 10) {
                    return fichero.readLines()
                        .drop(1)
                        .map { trabajadores -> trabajadores.split(";") }
                        .map {
                            it.map { it.trim() }
                            Trabajador(
                                _id = it[0].toString(),
                                nombre = it[1],
                                teléfono = it[2].toInt(),
                                email = it[3],
                                username = it[4],
                                contraseña = it[5].toByteArray(),
                                fechaContratacion = LocalDate.parse(it[6]),
                                especialidad = it[7],
                                salario = it[8].toInt(),
                                responsable = it[9].toBoolean()
                            )
                        }
                }else{
                    val f = fichero.readLines().first()
                    println(f)
                    throw Exception("La cabecera no es igual")
                }
            }else {
                throw Exception("El formato no es correcto")
            }
    }

    suspend fun  añadirDatos(){
        println("Añadimos los trabajadores")
        for (i in Data.trabajadores){
            trabajadorController.borrarTrabajador(i._id)
            trabajadorController.saveTrabajador(i)
        }
        println("Añadimos los propietarios")
        for (i in Data.propietarios){
            propietarioController.borrarPropietario(i._id)
            propietarioController.savePropietario(i)
        }
        println("Añadimos los vehiculos")
        for (i in Data.vehiculos){
            vehiculoController.borrarVehiculo(i._id)
            vehiculoController.saveVehiculo(i)
        }

        println("Añadimos las citas")
        for (i in Data.citas){
            citaController.borrarCita(i._id)
            citaController.saveCita(i)
        }

        println("Añadimos los informes")
        for (i in Data.informes){
            informeController.borrarInforme(i._id)
            informeController.saveInforme(i)
        }

        println("Buscar trabajador por id")
        val t = trabajadorController.findById("1")
        println("Trabajador con id $t")
        println("Buscar propietario por id")
        val p = propietarioController.findByIdPropietario("1")
        println("Propietario con id $p")

        println("Buscar vehiculo por id")
        val v = vehiculoController.findByIdVehiculo("1")
        println("Vehiculo con id $v")

        println("Buscar cita por id")
        val c = citaController.findByIdCita("1")
        println("Cita con id $c")

        println("Buscar informe por id")
        val i = informeController.findByIdInforme("1")
        println("Informe con id  $i")




        println("Borrar trabajador por id")
        trabajadorController.borrarTrabajador("1")
        println("Borrar propietario por id")
        propietarioController.borrarPropietario("1")
        println("Borrar vehiculo por id")
        vehiculoController.borrarVehiculo("1")
        println("Borrar cita por id")
        citaController.borrarCita("1")
        println("Borrar informe por id")
        informeController.borrarInforme("1")

    }

}