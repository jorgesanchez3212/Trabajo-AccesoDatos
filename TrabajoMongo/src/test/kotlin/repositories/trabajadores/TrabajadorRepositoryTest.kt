package repositories.trabajadores

import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Trabajador
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.trabajador.TrabajadorRepository
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrabajadorRepositoryTest {
    val repository = TrabajadorRepository()

    val trabajador = Trabajador(
    _id = "21",
    nombre = "Lucia Egido",
    teléfono = 601383547,
    email = "lucia@gmail.com",
    username = "luciaegido",
    contraseña = "luciaeslamejor".toByteArray(),
    fechaContratacion = LocalDate.of(2020, 5, 15),
    especialidad = Trabajador.Especialidad.MOTOR.name,
    salario = 2800,
    responsable = true
    )

    //Test del findAll de trabajadores
    @Test
    fun findAll()  = runBlocking {
        MongoDbManager.database.getCollection<Trabajador>().drop()
        repository.save(trabajador)
        val list = repository.findAll().toList()
        assertAll(
            { assertNotNull(list) },
            { assertEquals(1, list.size) }
        )
        repository.delete(trabajador._id)
    }

    // Test del findById de trabajador
    @Test
    fun findById() = runBlocking {
        repository.save(trabajador)
        val trabajadores = repository.findById("21")
        assertAll(
            { assertNotNull(trabajador) },
            { assertEquals("21", trabajador._id) }
        )
        repository.delete(trabajador._id)
    }

    // Test del save de trabajador
    @Test
    fun save() = runBlocking {
        repository.save(trabajador)
        val trabajadores = repository.findById("21")
        assertAll(
            { assertNotNull(trabajador) },
            { assertEquals("21", trabajador._id) }
        )
        repository.delete(trabajador._id)
    }

    // Test del update de trabajador
    @Test
    fun update() = runBlocking {
        repository.save(trabajador)
        repository.update(trabajador)
        val trabajadores = repository.findById("21")
        assertAll(
            { assertNotNull(trabajador) },
            { assertEquals("21", trabajador._id) }
        )
        repository.delete(trabajador._id)

    }

    // Test del delete de trabajador
    @Test
    fun delete() = runBlocking {
        repository.save(trabajador)
        val trabajadorr = repository.findById("21")
        if(trabajadorr != null){
            repository.delete(trabajador._id)
        }
        val trabajadorrr = repository.findById("21")

        assertAll(
            { assertNotNull(trabajadorr) },
            { assertNull(trabajadorrr) }
        )
    }

}