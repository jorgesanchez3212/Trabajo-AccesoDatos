package controllers


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
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehiculoControllerTest {


    var repository = VehiculoRepository()
    var repositoryCached = VehiculoRepositoryCached(VehiculosCache())
    var controller = VehiculoController(repository,repositoryCached)

    val entity = Vehiculo(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        marca = "Toyota",
        modelo = "Corolla",
        matricula = "ABC123",
        fechaMatriculacion = LocalDate.of(2022, 1, 15),
        fechaUltimaRevision = LocalDate.of(2022, 12, 30)
    )

    @Test
    fun findAll() = runTest {

        controller.saveVehiculo(entity)
        var res = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        controller.borrarVehiculo(entity.uuid)
    }

    @Test
    fun create() = runTest {

        controller.saveVehiculo(entity)
        var list = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        controller.borrarVehiculo(entity.uuid)
    }

    @Test
    fun update() = runTest {

        val entity1 = Vehiculo(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            marca = "Toyota",
            modelo = "Meme",
            matricula = "ABC123",
            fechaMatriculacion = LocalDate.of(2022, 1, 15),
            fechaUltimaRevision = LocalDate.of(2022, 12, 30)
        )
        controller.saveVehiculo(entity)
        controller.updateVehiculo(entity1)
        var list = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertNotEquals(entity.modelo,entity1.modelo) }
        )

        controller.borrarVehiculo(entity.uuid)
    }

    @Test
    fun delete() = runTest {

        controller.saveVehiculo(entity)
        var list = controller.findAllVehiculo()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        controller.borrarVehiculo(entity.uuid)
        var listBorrar = controller.findAllVehiculo()!!.toList()

        assertAll(
            { assertNotEquals(listBorrar.size, list.size) }
        )

    }
}