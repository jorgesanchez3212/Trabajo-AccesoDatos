package controllers


import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import repositories.propietario.PropietarioRepository
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropietarioControllerTest {


    var repository = PropietarioRepository()
    var controller = PropietarioController(repository)

    val entity = Propietario(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        dni = "67890123F",
        nombre = "MiAbuela",
        apellidos = "Gómez Ruiz",
        teléfono = "777777777"
    )


    @Test
    fun findAll() = runTest {

        controller.savePropietario(entity)
        var res = controller.findAllPropietario().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        controller.borrarPropietario(entity.uuid)
    }

    @Test
    fun create() = runTest {

        controller.savePropietario(entity)
        var list = controller.findAllPropietario().toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(entity.uuid)
    }

    @Test
    fun update() = runTest {

        val entity1 = Propietario(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            dni = "67890123F",
            nombre = "MiAbuela2",
            apellidos = "Gómez Ruiz",
            teléfono = "777777777"
        )
        controller.savePropietario(entity)
        controller.updatePropietario(entity1)
        var list = controller.findAllPropietario().toList()
        assertAll(
            { assertNotEquals(entity1.nombre, entity.nombre) }
        )

        repository.delete(entity.uuid)
    }

    @Test
    fun delete() = runTest {

        controller.savePropietario(entity)
        var list = controller.findAllPropietario().toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(entity.uuid)
        var listBorrar = controller.findAllPropietario().toList()

        assertAll(
            { assertNotEquals(listBorrar.size, list.size) }
        )

    }
}