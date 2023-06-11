package controllers

import db.Data
import db.MongoDbManager
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
        _id = "5",
        nombre = "Juan Pérez",
        teléfono = 123456789,
        email = "juan@gmail.com",
        username = "juanperez",
        contraseña = "password123".toByteArray(),
        fechaContratacion = LocalDate.of(2020, 5, 15),
        especialidad = Trabajador.Especialidad.MOTOR.name,
        salario = 2800,
        responsable = true
    )


    val propietario = Propietario(
        _id = "5",
        dni = "67890123F",
        nombre = "MiAbuela",
        apellidos = "Gómez Ruiz",
        teléfono = "777777777"
    )

    val vehiculo = Vehiculo(
        _id = "5",
        marca = "Toyota",
        modelo = "Corolla",
        matricula = "ABC123",
        fechaMatriculacion = LocalDate.of(2022, 1, 15),
        fechaUltimaRevision = LocalDate.of(2022, 12, 30)
    )

    val entity =  Cita(
        fechaHora = Data.fechaInicio.plusMinutes(30),
        idTrabajador = "7",
        idVehiculo = "7",
        idPropietario = "7"
    )

    @Test
    fun findAll() = runTest {
        MongoDbManager.database.getCollection<Cita>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var res = controller.findAllCita().toList()
        assertAll(
            {assertEquals(res[0]._id,entity._id)},
            {assertEquals(res[0].idPropietario,entity.idPropietario)},
            { assertEquals(1, res.size) }
        )
        controller.borrarCita(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }


    @Test
    fun create() = runTest {
        MongoDbManager.database.getCollection<Cita>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var entity1 = controller.findByIdCita(entity._id)
        assertAll(
            { assertNotNull( entity1)}
        )
        controller.borrarCita(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }


    @Test
    fun findById() = runTest {
        MongoDbManager.database.getCollection<Cita>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var entity1 = controller.findByIdCita(entity._id)
        assertAll(
            { assertNotNull( entity1)},
            { assertEquals(entity._id, entity1?._id) }
        )
        controller.borrarCita(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }

    @Test
    fun update() = runTest {
        MongoDbManager.database.getCollection<Cita>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)

        val entity1 = Cita(
            fechaHora = LocalDateTime.of(2000,6,6,21,12,36),
            idTrabajador = "7",
            idVehiculo = "7",
            idPropietario = "7"
        )
        controller.updateCita(entity1)
        var en = repositoryCached.findById(entity._id)
        assertAll(
            {assertNotEquals(en?.fechaHora,entity.fechaHora)},
            { assertEquals(en?.idPropietario, entity?.idPropietario) },
            { assertEquals(en?.idTrabajador, entity?.idVehiculo) },
            { assertEquals(en?.idVehiculo, entity?.idVehiculo) }


        )

        controller.borrarCita(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }

    @Test
    fun delete() = runTest {
        MongoDbManager.database.getCollection<Cita>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveCita(entity)
        var en = controller.findByIdCita(entity._id)
        assertAll(
            { assertNotNull(en) }
        )
        controller.borrarCita(entity._id)
        var listBorrar = controller.findAllCita().toList()

        assertAll(
            { assertEquals(0,listBorrar.size) }
        )

        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }


}