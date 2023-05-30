package controllers

import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import repositories.trabajador.TrabajadorRepository
import java.time.LocalDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrabajadorControllerTest {


    var repository = TrabajadorRepository()
    var controller = TrabajadorController(repository)

    val trabajador = Trabajador(
        _id = "1",
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


    @Test
    fun findAllTrabajadores() = runTest {
        MongoDbManager.database.getCollection<Trabajador>().drop()

        controller.saveTrabajador(trabajador)
        var res = controller.findAllTrabajadores().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        controller.borrarTrabajador(trabajador._id)
    }

    @Test
    fun createTrabajador() = runTest {
        MongoDbManager.database.getCollection<Trabajador>().drop()

        controller.saveTrabajador(trabajador)
        var list = controller.findAllTrabajadores().toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(trabajador._id)
    }

    @Test
    fun updateTrabajador() = runTest {
        MongoDbManager.database.getCollection<Trabajador>().drop()

        val trabajador1 = Trabajador(
            _id = "1",
            nombre = "Nina de Arco",
            teléfono = 123456789,
            email = "juan@gmail.com",
            username = "juanperez",
            contraseña = "password123".toByteArray(),
            fechaContratacion = LocalDate.of(2020, 5, 15),
            especialidad = Trabajador.Especialidad.MOTOR.name,
            salario = 2800,
            responsable = true
        )
        controller.saveTrabajador(trabajador)
        controller.updateTrabajador(trabajador1)
        var list = controller.findAllTrabajadores().toList()
        assertAll(
            { assertEquals(trabajador1.email, list[0].email) }
        )

        repository.delete(trabajador._id)
    }

    @Test
    fun deleteTrabajador() = runTest {
        MongoDbManager.database.getCollection<Trabajador>().drop()

        controller.saveTrabajador(trabajador)
        var list = controller.findAllTrabajadores().toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(trabajador._id)
        var listBorrar = controller.findAllTrabajadores().toList()

        assertAll(
            { assertNotEquals(listBorrar.size, list.size) }
        )

    }
}