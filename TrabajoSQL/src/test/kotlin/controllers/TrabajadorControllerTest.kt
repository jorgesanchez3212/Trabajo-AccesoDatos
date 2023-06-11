package controllers

import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import repositories.trabajador.TrabajadorRepository
import java.time.LocalDate
import java.util.*
import javax.persistence.Persistence
import javax.persistence.Query


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrabajadorControllerTest {


    var repository = TrabajadorRepository()
    var controller = TrabajadorController(repository)

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

    fun borrarDatosTabla() {
        val emf = Persistence.createEntityManagerFactory("default")
        val em = emf.createEntityManager()
        val tx = em.transaction
        tx.begin()

        // Borrar datos de cada tabla
        val consultaTrabajador = "DELETE FROM Trabajador"

        val queryTrabajador: Query = em.createQuery(consultaTrabajador)

        queryTrabajador.executeUpdate()
        tx.commit()
        em.close()
    }
    @Test
    fun findAllTrabajadores() = runTest {
        borrarDatosTabla()
        controller.saveTrabajador(trabajador)
        var res = controller.findAllTrabajadores()!!.toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        controller.borrarTrabajador(trabajador.uuid)
    }

    @Test
    fun createTrabajador() = runTest {
        borrarDatosTabla()
        controller.saveTrabajador(trabajador)
        var list = controller.findAllTrabajadores()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(trabajador.uuid)
    }

    @Test
    fun updateTrabajador() = runTest {
        borrarDatosTabla()
        val trabajador1 = Trabajador(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            nombre = "Juanito Pérez",
            telefono = 123456789,
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
        var list = controller.findAllTrabajadores()!!.toList()
        assertAll(
            { assertNotEquals(trabajador1.nombre, trabajador.nombre) }
        )

        repository.delete(trabajador.uuid)
    }

    @Test
    fun deleteTrabajador() = runTest {
        borrarDatosTabla()
        controller.saveTrabajador(trabajador)
        var list = controller.findAllTrabajadores()!!.toList()
        assertAll(
            { assertEquals(1, list.size) }
        )
        repository.delete(trabajador.uuid)
        var listBorrar = controller.findAllTrabajadores()!!.toList()

        assertAll(
            { assertNotEquals(listBorrar.size, list.size) }
        )

    }
}