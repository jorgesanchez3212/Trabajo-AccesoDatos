package repositories

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Vehiculo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.vehiculo.VehiculoRepository
import java.time.LocalDate
import java.util.*

class VehiculoRepositoryTest {

    val repository = VehiculoRepository()


    val entity = Vehiculo(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb2"),
        marca = "Mercedes-Benz",
        modelo = "E-Class",
        matricula = "STU901",
        fechaMatriculacion = LocalDate.of(2021, 3, 12),
        fechaUltimaRevision = LocalDate.of(2022, 7, 20)
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
            { Assertions.assertEquals(encontrado?.marca,entity.marca) },
            { Assertions.assertEquals(encontrado?.modelo,entity.modelo) }
        )

    }

    @Test
    fun save(): Unit = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(entity.uuid).getOrNull()
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(entity.marca, entityy?.marca) },
            { Assertions.assertEquals(entity?.modelo,entityy?.modelo) }
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