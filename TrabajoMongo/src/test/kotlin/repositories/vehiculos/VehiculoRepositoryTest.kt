package repositories.vehiculos

import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Trabajador
import models.Vehiculo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.vehiculo.VehiculoRepository
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehiculoRepositoryTest {
    val repository = VehiculoRepository()

    val vehiculo = Vehiculo(
        _id = "21",
        marca = "BMW",
        modelo = "W3",
        matricula = "ABC123",
        fechaMatriculacion = LocalDate.of(2022, 1, 15),
        fechaUltimaRevision = LocalDate.of(2022, 12, 30)
    )


    //Test del findAll de vehiculo
    @Test
    fun findAll(): Unit = runBlocking {
        MongoDbManager.database.getCollection<Vehiculo>().drop()
        repository.save(vehiculo)
        val list = repository.findAll().getOrNull()!!.toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list.size) }
        )
        repository.delete(vehiculo._id)
    }

    // Test del findById de vehiculo
    @Test
    fun findById(): Unit = runBlocking {
        repository.save(vehiculo)
        val vehiculos = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(vehiculo) },
            { Assertions.assertEquals("21", vehiculo._id) }
        )
        repository.delete(vehiculo._id)
    }

    // Test del save de vehiculo
    @Test
    fun save(): Unit = runBlocking {
        repository.save(vehiculo)
        val vehiculos = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(vehiculo) },
            { Assertions.assertEquals("21", vehiculo._id) }
        )
        repository.delete(vehiculo._id)
    }

    // Test del update de vehiculo
    @Test
    fun update(): Unit = runBlocking {
        repository.save(vehiculo)
        repository.update(vehiculo)
        val vehiculos = repository.findById("21").getOrNull()
        Assertions.assertAll(
            { Assertions.assertNotNull(vehiculos) },
            { Assertions.assertEquals("21", vehiculos?._id) }
        )
        repository.delete(vehiculos!!._id)

    }

    // Test del delete de vehiculo
    @Test
    fun delete() = runBlocking {
        repository.save(vehiculo)
        val vehiculoss = repository.findById("21")
        if (vehiculoss.getOrNull() != null) {
            repository.delete(vehiculo._id)
        }
        val vehiculosss = repository.findById("21").getOrNull()

        Assertions.assertAll(
            { Assertions.assertNotNull(vehiculoss) },
            { Assertions.assertNull(vehiculosss) }
        )
    }

}