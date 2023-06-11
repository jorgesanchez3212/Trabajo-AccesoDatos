package com.example.itvspringapplication.view

import com.example.itvspringapplication.controllers.*
import com.example.itvspringapplication.data.Data
import com.example.itvspringapplication.models.*
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.io.path.Path

@Component
class ItvView
    @Autowired constructor(
    private val citaController : CitaController,
    private val informeController: InformeController,
    private val propietarioController: PropietarioController,
    private val trabajadorController: TrabajadorController,
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
            trabajadorController.borrarTrabajador(it)
        }

    }

    fun exportarJSON(ruta: String, consultas: List<String>) {
        logger.debug { "Exportando archivo json" }
        if (Files.exists(Path(ruta))) {
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "consultas.json")
            fichero.writeText(json.encodeToString(consultas))
        }else {
            Files.createDirectories(Path(ruta))
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "consultas.json")
            fichero.writeText(json.encodeToString(consultas))
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

    suspend fun  añadirDatos(){
        val p = Propietario(
            _id = "9",
            dni = "23456789G",
            nombre = "Sara",
            apellidos = "Hernández García",
            teléfono = "444444444"
        )

        val t = Trabajador(
            _id = "9",
            nombre = "Ana Memes",
            teléfono = 777777777,
            email = "ana11@gmail.com",
            username = "anaaamemes",
            contraseña = "password123".toByteArray(),
            fechaContratacion = LocalDate.of(2021, 4, 18),
            especialidad = Trabajador.Especialidad.ELECTRICIDAD.name,
            salario = 1800,
            responsable = false
        )

        val v = Vehiculo(
            _id = "9",
            marca = "Toyota",
            modelo = "Corolla",
            matricula = "ABC12322",
            fechaMatriculacion = LocalDate.of(2022, 1, 15),
            fechaUltimaRevision = LocalDate.of(2022, 12, 30)
        )

        val i = Informe(
            _id = "9",
            frenado = 5,
            contaminación = 2.1,
            aptoFrenado = true,
            luces = true,
            apto = true,
            idTrabajador = t,
            idVehiculo = v,
            idPropietario = p
        )

        val fechaInicio = LocalDateTime.now()

        val c =  Cita(
            _id = "9",
            fechaHora = fechaInicio,
            idTrabajador = t._id,
            idVehiculo = v._id,
            idPropietario = p._id
        )

        //Añadir
        println("Añadiendo vehiculo")
        vehiculoController.saveVehiculo(v)
        println("Añadiendo trabajador")
        trabajadorController.saveTrabajador(t)
        println("Añadiendo propietario")
        propietarioController.savePropietario(p)
        println("Añadiendo el informe $i")
        informeController.saveInforme(i)
        println("Añadiendo la cita $c")
        citaController.saveCita(c)



        //Find All
        println("Todos los trabajadores")
        val b = trabajadorController.findAllTrabajadores()
        println(b.toList())
        println("Todos los propietarios")
        val pr = propietarioController.findAllPropietario()
        println(pr.toList())
        println("Todos los vehiculos")
        val veh = vehiculoController.findAllVehiculo()
        println(veh.toList())
        println("Todas las citas")
        val a = citaController.findAllCita()
        println(a.toList())



        //Delete
        println("Borrar trabajador por id")
        trabajadorController.borrarTrabajador(t)
        println("Borrar propietario por id")
        propietarioController.borrarPropietario(p)
        println("Borrar vehiculo por id")
        vehiculoController.borrarVehiculo(v)
        println("Borrar cita por id")
        citaController.borrarCita(c)
        println("Borrar informe por id")
        informeController.borrarInforme(i)

    }

    suspend fun borrarTodo(){
        trabajadorController.deleteAll()
        println("Trabajadores borrados")
        propietarioController.deleteAll()
        println("Propietarios borrados")
        vehiculoController.deleteAll()
        println("Vehiculos borrados")
        informeController.deleteAll()
        println("Informes borrados")
        citaController.deleteAll()
        println("Citas borrados")
    }

}