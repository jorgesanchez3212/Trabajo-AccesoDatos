package controllers


import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import repositories.propietario.PropietarioRepository


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropietarioControllerTest {


    var repository = PropietarioRepository()
    var controller = PropietarioController(repository)

    val entity = Propietario(
        _id = "6",
        dni = "67890123F",
        nombre = "Carlos",
        apellidos = "Gómez Ruiz",
        teléfono = "777777777"
    )


    @Test
    fun findAll() = runTest {
        MongoDbManager.database.getCollection<Propietario>().drop()

        controller.savePropietario(entity)
        var res = controller.findAllPropietario()!!.toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        controller.borrarPropietario(entity._id)
    }

    @Test
    fun create() = runTest {
        MongoDbManager.database.getCollection<Propietario>().drop()

        controller.savePropietario(entity)
        var list = controller.findAllPropietario()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(entity._id)
    }

    @Test
    fun update() = runTest {
        MongoDbManager.database.getCollection<Propietario>().drop()

        val entity1 = Propietario(
            _id = "6",
            dni = "67890123F",
            nombre = "MiAbuela",
            apellidos = "Gómez Ruiz",
            teléfono = "777777777"
        )
        controller.savePropietario(entity)
        controller.updatePropietario(entity1)
        var list = controller.findAllPropietario()!!.toList()
        assertAll(
            { assertEquals(entity1.dni, list[0].dni) }
        )

        repository.delete(entity._id)
    }

    @Test
    fun delete() = runTest {
        MongoDbManager.database.getCollection<Propietario>().drop()

        controller.savePropietario(entity)
        var list = controller.findAllPropietario()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(entity._id)
        var listBorrar = controller.findAllPropietario()!!.toList()

        assertAll(
            { assertNotEquals(listBorrar.size, list.size) }
        )

    }
}