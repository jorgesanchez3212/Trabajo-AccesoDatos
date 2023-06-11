package controllers


import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import repositories.vehiculo.VehiculoRepository
import repositories.vehiculo.VehiculoRepositoryCached
import services.vehiculos.VehiculosCache
import java.time.LocalDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehiculoControllerTest {


    var repository = VehiculoRepository()
    var repositoryCached = VehiculoRepositoryCached(VehiculosCache())
    var controller = VehiculoController(repository,repositoryCached)

    val entity = Vehiculo(
        _id = "1",
        marca = "Toyota",
        modelo = "Corolla",
        matricula = "ABC123",
        fechaMatriculacion = LocalDate.of(2022, 1, 15),
        fechaUltimaRevision = LocalDate.of(2022, 12, 30)
    )

    @Test
    fun findAll() = runTest {
        MongoDbManager.database.getCollection<Vehiculo>().drop()

        controller.saveVehiculo(entity)
        var res = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        controller.borrarVehiculo(entity._id)
    }

    @Test
    fun create() = runTest {
        MongoDbManager.database.getCollection<Vehiculo>().drop()

        controller.saveVehiculo(entity)
        var list = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        controller.borrarVehiculo(entity._id)
    }

    @Test
    fun update() = runTest {
        MongoDbManager.database.getCollection<Vehiculo>().drop()

        val entity1 = Vehiculo(
            _id = "1",
            marca = "Clio",
            modelo = "Corolla",
            matricula = "ABC123",
            fechaMatriculacion = LocalDate.of(2022, 1, 15),
            fechaUltimaRevision = LocalDate.of(2022, 12, 30)
        )
        controller.saveVehiculo(entity)
        controller.updateVehiculo(entity1)
        var list = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertEquals(entity1.modelo, list[0].modelo) }
        )

        controller.borrarVehiculo(entity._id)
    }

    @Test
    fun delete() = runTest {
        MongoDbManager.database.getCollection<Vehiculo>().drop()

        controller.saveVehiculo(entity)
        var list = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        controller.borrarVehiculo(entity._id)
        var listBorrar = controller.findAllVehiculo()!!.toList()

        assertAll(
            { assertNotEquals(listBorrar.size, list.size) }
        )

    }
}