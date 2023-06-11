package repositories

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Trabajador
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.trabajador.TrabajadorRepository
import java.time.LocalDate
import java.util.*

class TrabajadorRepositoryTest {
    val repository = TrabajadorRepository()

    val entity = Trabajador(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb2"),
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
    fun setUp(): Unit = runBlocking {
        repository.deleteAll()
    }

    @Test
    fun findAll(): Unit = runBlocking {
        repository.save(entity)
        val list = repository.findAll().getOrNull()!!.toList()
        assertAll(
            { assertNotNull(list) },
            { assertEquals(1, list.size) }
        )
        repository.delete(entity.uuid)
    }


    @Test
    fun findById() = runBlocking {
        val tr = repository.save(entity)
        var encontrado = repository.findById(UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb2")).getOrNull()
        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.email,entity.email) },
            { assertEquals(encontrado?.especialidad,entity.especialidad) },
            { assertEquals(encontrado?.nombre,entity.nombre) }

        )

    }


    @Test
    fun save(): Unit = runBlocking {
        val tr = repository.save(entity)
        val trabajadorr = repository.findById(entity.uuid).getOrNull()
        assertAll(
            { assertNotNull(trabajadorr) },
            { assertEquals(entity.email, trabajadorr?.email) },
            { assertEquals(entity?.especialidad,trabajadorr?.especialidad) },
            { assertEquals(entity?.nombre,trabajadorr?.nombre) }
        )
        repository.delete(entity.uuid)
    }


    @Test
    fun update() = runBlocking {
        val tr = repository.save(entity)
        repository.update(entity)
        val trabajadores = repository.findById(entity.uuid).getOrNull()
        assertAll(
            { assertNotNull(trabajadores) },
            { assertEquals(entity.uuid, trabajadores?.uuid) }
        )
        repository.delete(entity.uuid)

    }


    @Test
    fun delete() = runBlocking {
        val tr = repository.save(entity)
        val trabajadorr = repository.findById(entity.uuid).getOrNull()
        repository.delete(entity.uuid)

        val trabajadorrr = repository.findById(entity.uuid).getOrNull()

            assertNotNull(trabajadorr)
            assertNull(trabajadorrr)

    }

}