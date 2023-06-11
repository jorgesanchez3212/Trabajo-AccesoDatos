package controllers

import db.Data
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import repositories.cita.CitaRepository
import repositories.cita.CitaRepositoryCached
import repositories.propietario.PropietarioRepository
import repositories.trabajador.TrabajadorRepository
import repositories.vehiculo.VehiculoRepository
import repositories.vehiculo.VehiculoRepositoryCached
import services.citas.CitaCache
import services.vehiculos.VehiculosCache
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitaControllerTest {


    var repository = CitaRepository()
    var repositoryCached = CitaRepositoryCached(CitaCache())
    var controller = CitaController(repository,repositoryCached)

    var repositoryVehiculo = VehiculoRepository()
    var repositoryCachedVehiculo = VehiculoRepositoryCached(VehiculosCache())
    var controllerVehiculo = VehiculoController(repositoryVehiculo,repositoryCachedVehiculo)

    var repositoryTrabajador = TrabajadorRepository()
    var controllerTrabajador = TrabajadorController(repositoryTrabajador)


    var repositoryPropietario = PropietarioRepository()
    var controllerPropietario = PropietarioController(repositoryPropietario)



    val trabajador = Trabajador(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        nombre = "Juan Pérez",
        telefono = 123456789,
        email = "juan@gmail.com",
        username = "juanperez",
        contraseña = "password123".toByteArray(),
        fechaContratacion = LocalDate.of(2020, 5, 15),
        especialidad = Trabajador.Especialidad.MOTOR.name,
        salario = 2800,
        responsable = true
    )


    val propietario = Propietario(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        dni = "67890123F",
        nombre = "MiAbuela",
        apellidos = "Gómez Ruiz",
        teléfono = "777777777"
    )

    val vehiculo = Vehiculo(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        marca = "Toyota",
        modelo = "Corolla",
        matricula = "ABC123",
        fechaMatriculacion = LocalDate.of(2022, 1, 15),
        fechaUltimaRevision = LocalDate.of(2022, 12, 30)
    )

    val fechaInicio = LocalDateTime.now()
    val entity =  Cita(
        fechaHora = fechaInicio.plusMinutes(30),
        idTrabajador = trabajador,
        idVehiculo = vehiculo,
        idPropietario = propietario
    )

    @Test
    fun findAll() = runTest {


        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var res = controller.findAllCita().toList()
        assertAll(
            {assertEquals(res[0].uuid,entity.uuid)},
            {assertEquals(res[0].idPropietario,entity.idPropietario)},
            { assertEquals(1, res.size) }
        )
        controller.borrarCita(entity.uuid)
        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }


    @Test
    fun create() = runTest {

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var entity1 = controller.findByIdCita(entity.uuid)
        assertAll(
            { assertNotNull( entity1)}
        )
        controller.borrarCita(entity.uuid)
        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }


    @Test
    fun findById() = runTest {

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var entity1 = controller.findByIdCita(entity.uuid)
        assertAll(
            { assertNotNull( entity1)},
            { assertEquals(entity.uuid, entity1?.uuid) }
        )
        controller.borrarCita(entity.uuid)
        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }


    @Test
    fun delete() = runTest {

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var en = controller.findByIdCita(entity.uuid)
        assertAll(
            { assertNotNull(en) }
        )
        controller.borrarCita(entity.uuid)
        var listBorrar = controller.findAllCita().toList()

        assertAll(
            { assertEquals(0,listBorrar.size) }
        )

        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }


}