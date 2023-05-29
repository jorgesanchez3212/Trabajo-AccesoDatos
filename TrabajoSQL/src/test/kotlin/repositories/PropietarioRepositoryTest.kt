package repositories

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Propietario
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.propietario.PropietarioRepository

class PropietarioRepositoryTest {
    val repository = PropietarioRepository()

    val entity = Propietario(
        dni = "12345678A",
        nombre = "Juan",
        apellidos = "Pérez López",
        teléfono = "123456789"
    )

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun findAll()  = runBlocking {
        repository.save(entity)
        val list = repository.findAll().toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list.size) }
        )
        repository.delete(entity.uuid)
    }

    @Test
    fun findById() = runBlocking {
        val tr = repository.save(entity)
        var encontrado = repository.findById(tr.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(encontrado) },
            { Assertions.assertEquals(encontrado?.dni,entity.dni) },
            { Assertions.assertEquals(encontrado?.teléfono,entity.teléfono) },
            { Assertions.assertEquals(encontrado?.nombre,entity.nombre) }

        )

    }

    @Test
    fun save() = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(tr.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(tr.dni, entityy?.dni) },
            { Assertions.assertEquals(tr?.nombre,entityy?.nombre) },
            { Assertions.assertEquals(tr?.teléfono,entityy?.teléfono) }
        )
        repository.delete(entity.uuid)
    }

    @Test
    fun update() = runBlocking {
        val tr = repository.save(entity)
        repository.update(entity)
        val entityy = repository.findById(tr.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(tr.uuid, entityy?.uuid) }
        )
        repository.delete(tr.uuid)

    }

    @Test
    fun delete() = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(tr.uuid)
        repository.delete(tr.uuid)

        val entityyy = repository.findById(tr.uuid)

        Assertions.assertNotNull(entityy)
        Assertions.assertNull(entityyy)

    }

}