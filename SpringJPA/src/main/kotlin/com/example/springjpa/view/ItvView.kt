package com.example.springjpa.view

import com.example.springjpa.controllers.*
import com.example.springjpa.data.Data
import com.example.springjpa.dto.InformeDTO
import com.example.springjpa.models.*
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
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
        val trabajadores = trabajadorController.findAllTrabajadores()!!.toList()

        //Trabajador que mas gana sin ser responsable
        val trabajador = trabajadores.filter { it.responsable == false }.maxByOrNull { it.salario }
        println("El trabajador que mas gana sin ser responsable es ${trabajador.toString()}")

        //Salario medio de todos los trabajadores que no son responsables
        val salarioMedio = trabajadores.filter { it.responsable == false }.map { it.salario }.average()
        println("Salario medio de todos los trabajadores que no son responsables es $salarioMedio")

        //El salario medio de todos los trabajadores agrupados por especialidad.
        val salarioMedioAgrupadosPorEspecialidad : Map<String, Double> = trabajadores.groupBy { it.especialidad }.mapValues {
                entry -> entry.value.map { it.salario }.average() }
        println("Salario medio agrupados por especialidad es $salarioMedioAgrupadosPorEspecialidad")

        //- La el trabajador/a con menos antigüedad
        val trabajadorMenosAntiguedad = trabajadores.minByOrNull { it.fechaContratacion }
        println("La el trabajador/a con menos antigüedad $trabajadorMenosAntiguedad")

        // Trabajadores ordenados por especialidad y ordenados por antiguedad
        val trabajadoresOrdenados = trabajadores.sortedWith(compareBy(Trabajador::especialidad,Trabajador::fechaContratacion))
        println("Trabajadores ordenados por especialidad y ordenados por antiguedad son $trabajadoresOrdenados")

        val informe = InformeDTO(
            trabajador.toString(),
            salarioMedio,
            salarioMedioAgrupadosPorEspecialidad.toString(),
            trabajadorMenosAntiguedad.toString(),
            trabajadoresOrdenados.toString()
        )
        exportarJSONTrabajadores("."+File.separator +"metadata", Data.trabajadores)
        exportarJSONPropietarios("."+File.separator +"metadata", Data.propietarios)
        exportarJSONVehiculos("."+File.separator +"metadata", Data.vehiculos)


        exportarXML("."+File.separator +"metadata",informe)

        lista.forEach {
            trabajadorController.borrarTrabajador(it)
        }

    }


    fun exportarXML(ruta: String, informe : InformeDTO){
        if (Files.exists(Path(ruta))) {
            val fichero = File(ruta + File.separator + "informe.xml")
            val xml = XML {indentString = " "}
            val x = xml.encodeToString(InformeDTO.serializer(),informe)
            fichero.writeText(x)

        }else {
            Files.createDirectories(Path(ruta))

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

    fun exportarJSONPropietarios(ruta: String, propietario: List<Propietario>) {
        logger.debug { "Exportando archivo json" }
        if (Files.exists(Path(ruta))) {
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "propietarios.json")
            fichero.writeText(json.encodeToString(propietario))
        }else {
            Files.createDirectories(Path(ruta))
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "propietarios.json")
            fichero.writeText(json.encodeToString(propietario))
        }

    }

    fun exportarJSONVehiculos(ruta: String, vehiculo: List<Vehiculo>) {
        logger.debug { "Exportando archivo json" }
        if (Files.exists(Path(ruta))) {
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "vehiculos.json")
            fichero.writeText(json.encodeToString(vehiculo))
        }else {
            Files.createDirectories(Path(ruta))
            val json = Json { prettyPrint = true }
            val fichero = File(ruta + File.separator + "vehiculos.json")
            fichero.writeText(json.encodeToString(vehiculo))
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
                            uuid = UUID.fromString(it[0]),
                            nombre = it[1],
                            telefono = it[2].toInt(),
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
        val p = Propietario(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            dni = "23456789G",
            nombre = "Sara",
            apellidos = "Hernández García",
            teléfono = "444444444"
        )

        val t = Trabajador(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            nombre = "Ana Memes",
            telefono = 777777777,
            email = "ana11@gmail.com",
            username = "anaaamemes",
            contraseña = "password123".toByteArray(),
            fechaContratacion = LocalDate.of(2021, 4, 18),
            especialidad = Trabajador.Especialidad.ELECTRICIDAD.name,
            salario = 1800,
            responsable = false
        )

        val v = Vehiculo(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            marca = "Toyota",
            modelo = "Corolla",
            matricula = "ABC12322",
            fechaMatriculacion = LocalDate.of(2022, 1, 15),
            fechaUltimaRevision = LocalDate.of(2022, 12, 30)
        )

        val i = Informe(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
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
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            fechaHora = fechaInicio,
            trabajador = t,
            vehiculo = v,
            propietario = p
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
        println("Borrar cita por id")
        citaController.borrarCita(c)
        println("Borrar informe por id")
        informeController.borrarInforme(i)
        println("Borrar trabajador por id")
        trabajadorController.borrarTrabajador(t)
        println("Borrar propietario por id")
        propietarioController.borrarPropietario(p)
        println("Borrar vehiculo por id")
        vehiculoController.borrarVehiculo(v)


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


    suspend fun state(){
        citaController.state.collect{
            println("SE HA PRODUCIDO CAMBIO EN CITAS 💻 -> $it")
        }
    }

    suspend fun menu(){
        while (true) {
            println("""
            Elige una opcion:💻🫡
                1 -> Añadir Datos
                2 -> Exportar Informe XML y Entidades en JSON
                3 -> Salir
        """.trimIndent())
            val respuesta = readln()
            when (respuesta){
                "1" -> añadirDatos()
                "2" -> informes("data" + File.separator+"trabajadores.csv")
                "3" -> System.exit(0)
            }
        }


    }
}