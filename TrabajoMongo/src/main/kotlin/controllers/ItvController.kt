package controllers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.*
import org.litote.kmongo.newId
import repositories.cita.ICitaRepository
import repositories.informe.IInformeRepository
import repositories.propietario.IPropietarioRepository
import repositories.trabajador.ITrabajadorRepository
import repositories.vehiculo.IVehiculoRepository
import utils.Contraseña
import java.time.LocalDate
import java.time.LocalDateTime

class ItvController(
    private val trabajadoresRepository : ITrabajadorRepository,
    private val vehiculoRepository : IVehiculoRepository,
    private val propietarioRepository: IPropietarioRepository,
    private val informeRepository: IInformeRepository,
    private val citaRepository: ICitaRepository
) {

    //----------------------------------------------------TRABAJADORES-------------------------------------

    suspend fun findAllTrabajadores() : Flow<Trabajador>{
        return trabajadoresRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun saveTrabajador(){
        println("Introduce el nombre")
        val nombre = readln()
        println("Introduce el telefono")
        val telefono = readln().toInt()
        println("Introduce el email")
        var email = readln()
        var resultado = trabajadoresRepository.findByEmail(email)
        while (resultado != null) {
            println("Introduce el email")
            email = readln()
            resultado = trabajadoresRepository.findByEmail(email)
        }
        println("Introduce el username")
        var username = readln()
        var resultadoUsername = trabajadoresRepository.findByEmail(username)
        while (resultadoUsername != null) {
            println("Introduce otro username, ese ya esta en uso")
            username = readln()
            resultadoUsername = trabajadoresRepository.findByEmail(username)
        }
        println("Introduce el contraseña")
        val contraseña = readln()
        val contraseñaCifrada = Contraseña.encriptarContraseña(contraseña)

        println("Introduce la especialidad - ELECTRICIDAD/MOTOR/INTERIOR/MECANICA")
        var especialidad = readln()
        if (especialidad.equals("ELECTRICIDAD")) {
            especialidad = Trabajador.Especialidad.ELECTRICIDAD.name
        }else if (especialidad.equals("MOTOR")) {
            especialidad = Trabajador.Especialidad.MOTOR.name
        }else if (especialidad.equals("INTERIOR")){
            especialidad = Trabajador.Especialidad.INTERIOR.name
        }else{
            especialidad = Trabajador.Especialidad.MECANICA.name
        }

        println("¿Es responsable? SI/NO")
        val responsable = readln()
        val responsableBoolean = false

        if (responsable.equals("SI")){
            responsableBoolean == true
        }else {
            responsableBoolean == false
        }

        var salario : Int
        if (especialidad.equals("ELECTRICIDAD")) {
            salario = 1800
        }else if (especialidad.equals("MOTOR")) {
            salario = 1700
        }else if (especialidad.equals("INTERIOR")){
            salario = 1750
        }else{
            salario = 1600
        }

        if (responsableBoolean == true){
            salario += 1000
        }

        val trabajador = Trabajador(newId<Trabajador>().toString(),nombre,telefono,email,username,contraseñaCifrada, LocalDate.now(), especialidad,salario,responsableBoolean)
        trabajadoresRepository.save(trabajador)
    }



    suspend fun findByIdTrabajador() : Trabajador?{
        println("Introduce el id del trabajador")
        val id = readln()
        val trabajador = trabajadoresRepository.findById(id)
        println("El trabajador con id $id es ${trabajador.toString()}")
        return trabajador
    }



    suspend fun updateTrabajador() : Trabajador?{
        println("Introduce el id del trabajador")
        val id = readln()
        val trabajador = trabajadoresRepository.findById(id)
        if (trabajador == null){
            println("El trabajador con id $id no existe")
            return null
        }else{
            println("El trabajador con id $id es ${trabajador.toString()}")

            println("Introduce el nombre a actualizar")
            val nombre = readln()
            println("Introduce el telefono a actualizar")
            val telefono = readln().toInt()
            println("Introduce el email")
            var email = readln()
            var resultado = trabajadoresRepository.findByEmail(email)
            while (resultado != null) {
                println("Introduce el email a actualizar")
                email = readln()
                resultado = trabajadoresRepository.findByEmail(email)
            }
            println("Introduce el username a actualizar")
            var username = readln()
            var resultadoUsername = trabajadoresRepository.findByEmail(username)
            while (resultadoUsername != null) {
                println("Introduce otro username, ese ya esta en uso")
                username = readln()
                resultadoUsername = trabajadoresRepository.findByEmail(username)
            }
            println("Introduce el contraseña a actualizar")
            val contraseña = readln()
            val contraseñaCifrada = Contraseña.encriptarContraseña(contraseña)

            println("Introduce la especialidad a actualizar - ELECTRICIDAD/MOTOR/INTERIOR/MECANICA")
            var especialidad = readln()
            if (especialidad.equals("ELECTRICIDAD")) {
                especialidad = Trabajador.Especialidad.ELECTRICIDAD.name
            }else if (especialidad.equals("MOTOR")) {
                especialidad = Trabajador.Especialidad.MOTOR.name
            }else if (especialidad.equals("INTERIOR")){
                especialidad = Trabajador.Especialidad.INTERIOR.name
            }else{
                especialidad = Trabajador.Especialidad.MECANICA.name
            }

            println("¿Es responsable? SI/NO")
            val responsable = readln()
            val responsableBoolean = false

            if (responsable.equals("SI")){
                responsableBoolean == true
            }else {
                responsableBoolean == false
            }

            var salario : Int
            if (especialidad.equals("ELECTRICIDAD")) {
                salario = 1800
            }else if (especialidad.equals("MOTOR")) {
                salario = 1700
            }else if (especialidad.equals("INTERIOR")){
                salario = 1750
            }else{
                salario = 1600
            }

            if (responsableBoolean == true){
                salario += 1000
            }

            val trabajadorActualizado = Trabajador(trabajador._id,nombre,telefono,email,username,contraseñaCifrada,trabajador.fechaContratacion,especialidad,salario,responsableBoolean)
            trabajadoresRepository.update(trabajadorActualizado)
            return trabajadorActualizado
        }
    }


    suspend fun borrarTrabajador(){
        println("Introduce el id del trabajador a borrar")
        val id = readln()
        trabajadoresRepository.delete(id)
        println("El trabajador con id $id ha sido borrado")
    }






    //---------------------------------------------------------VEHICULO---------------------------------------------

    suspend fun findAllVehiculo() : Flow<Vehiculo>{
        return vehiculoRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun saveVehiculo(){
        println("Introduce la marca")
        val marca = readln()
        println("Introduce el modelo")
        val modelo = readln()
        println("Introduce la matricula")
        val matricula = readln()


        println("Introduce la fecha de matriculacion")
        println("Introduce el año en 4 digitos")
        val año = readln()
        println("Introduce el mes en 2 digitos")
        val mes = readln()
        println("Introduce el dia en 2 digitos")
        val dia = readln()

        val fechaMatriculacion = LocalDate.parse("$año/$mes/$dia")



        println("Introduce la fecha de ultima revision")
        println("Introduce el año en 4 digitos")
        val añoRevision = readln()
        println("Introduce el mes en 2 digitos")
        val mesRevision = readln()
        println("Introduce el dia en 2 digitos")
        val diaRevision = readln()
        val fechaRevision = LocalDate.parse("$añoRevision/$mesRevision/$diaRevision")

        val vehiculo = Vehiculo(newId<Vehiculo>().toString(), marca,modelo,matricula,fechaMatriculacion,fechaRevision)
        vehiculoRepository.save(vehiculo)

    }


    suspend fun findByIdVehiculo() : Vehiculo?{
        println("Introduce el id del vehiculo")
        val id = readln()
        val vehiculo = vehiculoRepository.findById(id)
        println("El vehiculo con id $id es ${vehiculo.toString()}")
        return vehiculo
    }

    suspend fun borrarVehiculo(){
        println("Introduce el id del vehiculo a borrar")
        val id = readln()
        vehiculoRepository.delete(id)
        println("El vehiculo con id $id ha sido borrado")
    }


    suspend fun updateVehiculo(){
        println("Introduce el id del vehiculo a actualizar")
        val id = readln()
        val vehiculo = vehiculoRepository.findById(id)
        if (vehiculo!= null){
            println("Introduce la marca a actualizar")
            val marca = readln()
            println("Introduce el modelo a actualizar")
            val modelo = readln()
            println("Introduce la matricula a actualizar")
            val matricula = readln()


            println("Introduce la fecha de matriculacion a actualizar")
            println("Introduce el año en 4 digitos")
            val año = readln()
            println("Introduce el mes en 2 digitos")
            val mes = readln()
            println("Introduce el dia en 2 digitos")
            val dia = readln()

            val fechaMatriculacion = LocalDate.parse("$año/$mes/$dia")



            println("Introduce la fecha de ultima revision a actualizar")
            println("Introduce el año en 4 digitos")
            val añoRevision = readln()
            println("Introduce el mes en 2 digitos")
            val mesRevision = readln()
            println("Introduce el dia en 2 digitos")
            val diaRevision = readln()
            val fechaRevision = LocalDate.parse("$añoRevision/$mesRevision/$diaRevision")

            val vehiculoupdate = Vehiculo(vehiculo._id, marca,modelo,matricula,fechaMatriculacion,fechaRevision)
            vehiculoRepository.update(vehiculoupdate)
        }

    }




    //----------------------------------------------PROPIETARIO------------------------------------------------

    suspend fun findAllPropietario() : Flow<Propietario>{
        return propietarioRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun savePropietario(){
        println("Introduce el dni")
        val dni = readln()
        println("Introduce el nombre")
        val nombre = readln()
        println("Introduce el apellido")
        val apellido = readln()
        println("Introduce el numero de telefono")
        val telefono = readln()

        val propietario = Propietario(newId<Propietario>().toString(),dni,nombre,apellido,telefono)
        propietarioRepository.save(propietario)
    }


    suspend fun findByIdPropietario() : Propietario?{
        println("Introduce el id del propietario")
        val id = readln()
        val propietario = propietarioRepository.findById(id)
        println("El propietario con id $id es ${propietario.toString()}")
        return propietario
    }

    suspend fun borrarPropietario(){
        println("Introduce el id del propietario a borrar")
        val id = readln()
        propietarioRepository.delete(id)
        println("El propietario con id $id ha sido borrado")
    }


    suspend fun updatePropietario(){
        println("Introduce el id del propietario a actualizar")
        val id = readln()
        val propietario = propietarioRepository.findById(id)
        if (propietario != null){
            println("Introduce el dni a actualizar")
            val dni = readln()
            println("Introduce el nombre a actualizar")
            val nombre = readln()
            println("Introduce el apellido a actualizar")
            val apellido = readln()
            println("Introduce el numero de telefono a actualizar")
            val telefono = readln()

            val propietarioupdate = Propietario(propietario._id,dni,nombre,apellido,telefono)
            propietarioRepository.update(propietarioupdate)
        }
    }




    //-----------------------------------------------------INFORME----------------------------------------



    suspend fun findAllInforme() : Flow<Informe>{
        return informeRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun saveInforme(){
        println("Introduce el frenado")
        val frenado = readln().toInt()
        println("Introduce la contaminacion")
        val contaminacion = readln().toDouble()
        println("Introduce si es apto en el frenado - SI/NO")
        val aptoFrenado = readln()
        val aptoFrenadoBoolean = false

        if (aptoFrenado.equals("SI")){
            aptoFrenadoBoolean == true
        }else {
            aptoFrenadoBoolean == false
        }
        println("Introduce si es apto en las luces - SI/NO")
        val aptoLuces = readln()
        val aptoLucesBoolean = false

        if (aptoLuces.equals("SI")){
            aptoLucesBoolean == true
        }else {
            aptoLucesBoolean == false
        }
        println("Introduce si es apto el coche - SI/NO")
        val aptoCoche = readln()
        val aptoCocheBoolean = false

        if (aptoCoche.equals("SI")){
            aptoCocheBoolean == true
        }else {
            aptoCocheBoolean == false
        }
        println("Introduce el id del propietario")
        val idPropietario = readln()
        println("Introduce el id del vehiculo")
        val idVehiculo = readln()
        println("Introduce el id del trabajador")
        val idTrabajador = readln()

        val informe = Informe(newId<Informe>().toString(),frenado,contaminacion,aptoFrenadoBoolean,aptoLucesBoolean,aptoCocheBoolean,idTrabajador,idVehiculo,idPropietario)
        informeRepository.save(informe)

    }


    suspend fun findByIdInforme() : Informe?{
        println("Introduce el id del informe")
        val id = readln()
        val informe = informeRepository.findById(id)
        println("El informe con id $id es ${informe.toString()}")
        return informe
    }

    suspend fun borrarInforme(){
        println("Introduce el id del informe a borrar")
        val id = readln()
        informeRepository.delete(id)
        println("El informe con id $id ha sido borrado")
    }


    suspend fun updateInforme(){
        println("Introduce el id del informe")
        val id = readln()
        val informe = informeRepository.findById(id)
        if (informe != null){
            println("Introduce el frenado a actualizar")
            val frenado = readln().toInt()
            println("Introduce la contaminacion a actualizar")
            val contaminacion = readln().toDouble()
            println("Introduce si es apto en el frenado a actualizar - SI/NO")
            val aptoFrenado = readln()
            val aptoFrenadoBoolean = false

            if (aptoFrenado.equals("SI")){
                aptoFrenadoBoolean == true
            }else {
                aptoFrenadoBoolean == false
            }
            println("Introduce si es apto en las luces a actualizar - SI/NO")
            val aptoLuces = readln()
            val aptoLucesBoolean = false

            if (aptoLuces.equals("SI")){
                aptoLucesBoolean == true
            }else {
                aptoLucesBoolean == false
            }
            println("Introduce si es apto el coche a actualizar - SI/NO")
            val aptoCoche = readln()
            val aptoCocheBoolean = false

            if (aptoCoche.equals("SI")){
                aptoCocheBoolean == true
            }else {
                aptoCocheBoolean == false
            }
            println("Introduce el id del propietario a actualizar")
            val idPropietario = readln()
            println("Introduce el id del vehiculo a actualizar")
            val idVehiculo = readln()
            println("Introduce el id del trabajador a actualizar")
            val idTrabajador = readln()

            val informeupdate = Informe(informe._id,frenado,contaminacion,aptoFrenadoBoolean,aptoLucesBoolean,aptoCocheBoolean,idTrabajador,idVehiculo,idPropietario)
            informeRepository.update(informeupdate)
        }

    }


    //----------------------------------------------------CITA--------------------------------------------------


    suspend fun findAllCita() : Flow<Cita>{
        return citaRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun saveCita(){
        println("Introduce la fecha y la hora de la cita")
        println("Introduce el año en 4 digitos")
        val año = readln()
        println("Introduce el mes en 2 digitos")
        val mes = readln()
        println("Introduce el dia en 2 digitos")
        val dia = readln()
        println("Introduce la hora")
        val hora = readln()

        val fechaHoraCita = LocalDateTime.parse("$año/$mes/$dia - $hora")
        println("Introduce el id del propietario")
        val idPropietario  = readln()
        println("Introduce el id del vehiculo")
        val idVehiculo  = readln()
        println("Introduce el id del trabajador")
        val idTrabajador  = readln()

        val cita = Cita(newId<Cita>().toString(),fechaHoraCita,idTrabajador,idVehiculo,idPropietario)
        citaRepository.save(cita)


    }


    suspend fun findByIdCita() : Cita?{
        println("Introduce el id de la cita")
        val id = readln()
        val cita = citaRepository.findById(id)
        println("La cita con id $id es ${cita.toString()}")
        return cita
    }

    suspend fun borrarCita(){
        println("Introduce el id de la cita a borrar")
        val id = readln()
        citaRepository.delete(id)
        println("La cita con id $id ha sido borrada")
    }


    suspend fun updateCita(){
        println("Introduce el id de la cita a actualizar")
        val id = readln()
        val cita = citaRepository.findById(id)

        if (cita != null){

            println("Introduce la fecha y la hora de la cita a actualizar")
            println("Introduce el año en 4 digitos")
            val año = readln()
            println("Introduce el mes en 2 digitos")
            val mes = readln()
            println("Introduce el dia en 2 digitos")
            val dia = readln()
            println("Introduce la hora")
            val hora = readln()

            val fechaHoraCita = LocalDateTime.parse("$año/$mes/$dia - $hora")
            println("Introduce el id del propietario a actualizar")
            val idPropietario  = readln()
            println("Introduce el id del vehiculo a actualizar")
            val idVehiculo  = readln()
            println("Introduce el id del trabajador a actualizar")
            val idTrabajador  = readln()

            val citaupdate = Cita(cita._id,fechaHoraCita,idTrabajador,idVehiculo,idPropietario)
            citaRepository.update(citaupdate)
        }
    }



}