package repositories

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Trabajador
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.trabajador.TrabajadorRepository
import java.time.LocalDate

class TrabajadorRepositoryTest {
    val repository = TrabajadorRepository()

    val trabajador = Trabajador(
        nombre = "Lucia Egido",
        telefono = 601333947,
        email = "lucia@gmail.com",
        username = "luciaegido",
        contraseña = "luciaeslamejor".toByteArray(),
        fechaContratacion = LocalDate.of(2020, 5, 15),
        especialidad = Trabajador.Especialidad.MOTOR.name,
        salario = 2800,
        responsable = true
    )


    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }
    //Test del findAll de trabajadores
    @Test
    fun findAll()  = runBlocking {
        repository.save(trabajador)
        val list = repository.findAll().toList()
        assertAll(
            { assertNotNull(list) },
            { assertEquals(1, list.size) }
        )
        repository.delete(trabajador.uuid)
    }

    // Test del findById de trabajador
    @Test
    fun findById() = runBlocking {
        val tr = repository.save(trabajador)
        var encontrado = repository.findById(tr.uuid)
        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.email,trabajador.email) },
            { assertEquals(encontrado?.especialidad,trabajador.especialidad) },
            { assertEquals(encontrado?.nombre,trabajador.nombre) }

        )

    }

    // Test del save de trabajador
    @Test
    fun save() = runBlocking {
        val tr = repository.save(trabajador)
        val trabajadorr = repository.findById(tr.uuid)
        assertAll(
            { assertNotNull(trabajadorr) },
            { assertEquals(tr.email, trabajadorr?.email) },
            { assertEquals(tr?.especialidad,trabajadorr?.especialidad) },
            { assertEquals(tr?.nombre,trabajadorr?.nombre) }
        )
        repository.delete(trabajador.uuid)
    }

    // Test del update de trabajador
    @Test
    fun update() = runBlocking {
        val tr = repository.save(trabajador)
        repository.update(trabajador)
        val trabajadores = repository.findById(tr.uuid)
        assertAll(
            { assertNotNull(trabajadores) },
            { assertEquals(tr.uuid, trabajadores?.uuid) }
        )
        repository.delete(tr.uuid)

    }

    // Test del delete de trabajador
    @Test
    fun delete() = runBlocking {
        val tr = repository.save(trabajador)
        val trabajadorr = repository.findById(tr.uuid)
        repository.delete(tr.uuid)

        val trabajadorrr = repository.findById(tr.uuid)

            assertNotNull(trabajadorr)
            assertNull(trabajadorrr)

    }

}