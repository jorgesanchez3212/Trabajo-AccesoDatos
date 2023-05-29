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

    val entity = Trabajador(
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

    @Test
    fun findAll()  = runBlocking {
        repository.save(entity)
        val list = repository.findAll().toList()
        assertAll(
            { assertNotNull(list) },
            { assertEquals(1, list.size) }
        )
        repository.delete(entity.uuid)
    }


    @Test
    fun findById() = runBlocking {
        val tr = repository.save(entity)
        var encontrado = repository.findById(tr.uuid)
        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.email,entity.email) },
            { assertEquals(encontrado?.especialidad,entity.especialidad) },
            { assertEquals(encontrado?.nombre,entity.nombre) }

        )

    }


    @Test
    fun save() = runBlocking {
        val tr = repository.save(entity)
        val trabajadorr = repository.findById(tr.uuid)
        assertAll(
            { assertNotNull(trabajadorr) },
            { assertEquals(tr.email, trabajadorr?.email) },
            { assertEquals(tr?.especialidad,trabajadorr?.especialidad) },
            { assertEquals(tr?.nombre,trabajadorr?.nombre) }
        )
        repository.delete(entity.uuid)
    }


    @Test
    fun update() = runBlocking {
        val tr = repository.save(entity)
        repository.update(entity)
        val trabajadores = repository.findById(tr.uuid)
        assertAll(
            { assertNotNull(trabajadores) },
            { assertEquals(tr.uuid, trabajadores?.uuid) }
        )
        repository.delete(tr.uuid)

    }


    @Test
    fun delete() = runBlocking {
        val tr = repository.save(entity)
        val trabajadorr = repository.findById(tr.uuid)
        repository.delete(tr.uuid)

        val trabajadorrr = repository.findById(tr.uuid)

            assertNotNull(trabajadorr)
            assertNull(trabajadorrr)

    }

}