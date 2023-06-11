package repositories

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Propietario
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.propietario.PropietarioRepository
import java.util.*

class PropietarioRepositoryTest {
    val repository = PropietarioRepository()

    val entity = Propietario(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb2"),
        dni = "12345678A",
        nombre = "Juan",
        apellidos = "Pérez López",
        teléfono = "123456789"
    )

    @BeforeEach
    fun setUp(): Unit = runBlocking {
        repository.deleteAll()
    }

    @Test
    fun findAll(): Unit = runBlocking {
        repository.save(entity)
        val list = repository.findAll().getOrNull()!!.toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list.size) }
        )
        repository.delete(entity.uuid)
    }

    @Test
    fun findById() = runBlocking {
        val tr = repository.save(entity)
        var encontrado = repository.findById(UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb2")).getOrNull()
        Assertions.assertAll(
            { Assertions.assertNotNull(encontrado) },
            { Assertions.assertEquals(encontrado?.dni,entity.dni) },
            { Assertions.assertEquals(encontrado?.teléfono,entity.teléfono) },
            { Assertions.assertEquals(encontrado?.nombre,entity.nombre) }

        )

    }

    @Test
    fun save(): Unit = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(entity.uuid).getOrNull()
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(entity.dni, entityy?.dni) },
            { Assertions.assertEquals(entity?.nombre,entityy?.nombre) },
            { Assertions.assertEquals(entity?.teléfono,entityy?.teléfono) }
        )
        repository.delete(entity.uuid)
    }

    @Test
    fun update(): Unit = runBlocking {
        val tr = repository.save(entity)
        repository.update(entity)
        val entityy = repository.findById(entity.uuid).getOrNull()
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(entity.uuid, entityy?.uuid) }
        )
        repository.delete(entity.uuid)

    }

    @Test
    fun delete() = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(entity.uuid).getOrNull()
        repository.delete(entity.uuid)

        val entityyy = repository.findById(entity.uuid).getOrNull()

        Assertions.assertNotNull(entityy)
        Assertions.assertNull(entityyy)

    }

}