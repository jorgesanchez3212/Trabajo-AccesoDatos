package controllers


import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import repositories.informe.InformeRepository
import repositories.informe.InformeRepositoryCached
import repositories.propietario.PropietarioRepository
import repositories.trabajador.TrabajadorRepository
import repositories.vehiculo.VehiculoRepository
import repositories.vehiculo.VehiculoRepositoryCached
import services.informes.InformesCache
import services.vehiculos.VehiculosCache
import java.time.LocalDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InformeControllerTest {


    var repository = InformeRepository()
    var repositoryCached = InformeRepositoryCached(InformesCache())
    var controller = InformeController(repository,repositoryCached)

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

    val entity =  Informe(
        _id = "5",
        frenado = 5,
        contaminación = 2.5,
        aptoFrenado = true,
        luces = true,
        apto = true,
        idTrabajador = "5",
        idVehiculo = "5",
        idPropietario = "5"
    )

    @Test
    fun findAll() = runTest {
        MongoDbManager.database.getCollection<Informe>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var res = controller.findAllInforme()!!.toList()
        assertAll(
            {assertEquals(res[0],entity)},
            { assertEquals(1, res.size) }
        )
        controller.borrarInforme(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }


    @Test
    fun create() = runTest {
        MongoDbManager.database.getCollection<Informe>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var entity1 = controller.findByIdInforme(entity._id)
        assertAll(
            { assertNotNull( entity1)}
        )
        controller.borrarInforme(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }


    @Test
    fun findById() = runTest {
        MongoDbManager.database.getCollection<Informe>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var entity1 = controller.findByIdInforme(entity._id)
        assertAll(
            { assertNotNull( entity1)},
            { assertEquals(entity, entity1) }
        )
        controller.borrarInforme(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }

    @Test
    fun update() = runTest {
        MongoDbManager.database.getCollection<Informe>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)

        val entity1 = Informe(
            _id = "5",
            frenado = 6,
            contaminación = 2.5,
            aptoFrenado = true,
            luces = true,
            apto = true,
            idTrabajador = "5",
            idVehiculo = "5",
            idPropietario = "5"
        )
        controller.updateInforme(entity1)
        var en = controller.findByIdInforme(entity._id)
        assertAll(
            { assertNotEquals(en?.frenado, entity?.frenado) },
            { assertEquals(en?.contaminación, entity?.contaminación) }

        )

        controller.borrarInforme(entity._id)
        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }

    @Test
    fun delete() = runTest {
        MongoDbManager.database.getCollection<Informe>().drop()
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        MongoDbManager.database.getCollection<Trabajador>().drop()
        MongoDbManager.database.getCollection<Propietario>().drop()

        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var en = controller.findByIdInforme(entity._id)
        assertAll(
            { assertNotNull(en) }
        )
        controller.borrarInforme(entity._id)
        var listBorrar = controller.findAllInforme()!!.toList()

        assertAll(
            { assertEquals(0,listBorrar.size) }
        )

        controllerPropietario.borrarPropietario(propietario._id)
        controllerTrabajador.borrarTrabajador(trabajador._id)
        controllerVehiculo.borrarVehiculo(vehiculo._id)
    }


}