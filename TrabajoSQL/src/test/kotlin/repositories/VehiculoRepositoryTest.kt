package repositories

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Vehiculo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.vehiculo.VehiculoRepository
import java.time.LocalDate

class VehiculoRepositoryTest {

    val repository = VehiculoRepository()


    val entity = Vehiculo(
        marca = "Mercedes-Benz",
        modelo = "E-Class",
        matricula = "STU901",
        fechaMatriculacion = LocalDate.of(2021, 3, 12),
        fechaUltimaRevision = LocalDate.of(2022, 7, 20)
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
            { Assertions.assertEquals(encontrado?.marca,entity.marca) },
            { Assertions.assertEquals(encontrado?.modelo,entity.modelo) }
        )

    }

    @Test
    fun save() = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(tr.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(tr.marca, entityy?.marca) },
            { Assertions.assertEquals(tr?.modelo,entityy?.modelo) }
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