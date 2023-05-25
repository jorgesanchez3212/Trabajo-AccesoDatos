package repositories.citas

import db.Data
import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Cita
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.cita.CitaRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitasRepositoryTest {
    val repository = CitaRepository()

    val cita = Cita(
        _id = "21",
        fechaHora = Data.fechaInicio.plusMinutes(30),
        idTrabajador = "1",
        idVehiculo = "1",
        idPropietario = "1"
    )


    //Test del findAll de cita
    @Test
    fun findAll()  = runBlocking {
        MongoDbManager.database.getCollection<Cita>().drop()
        repository.save(cita)
        val list = repository.findAll().toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list.size) }
        )
        repository.delete(cita._id)
    }

    // Test del findById de cita
    @Test
    fun findById() = runBlocking {
        repository.save(cita)
        val citaas = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(cita) },
            { Assertions.assertEquals("21", cita._id) }
        )
        repository.delete(cita._id)
    }

    // Test del save de cita
    @Test
    fun save() = runBlocking {
        repository.save(cita)
        val citas = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(cita) },
            { Assertions.assertEquals("21", cita._id) }
        )
        repository.delete(cita._id)
    }

    // Test del update de cita
    @Test
    fun update() = runBlocking {
        repository.save(cita)
        repository.update(cita)
        val citas = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(cita) },
            { Assertions.assertEquals("21", cita._id) }
        )
        repository.delete(cita._id)

    }

    // Test del delete de cita
    @Test
    fun delete() = runBlocking {
        repository.save(cita)
        val citas = repository.findById("21")
        if(citas != null){
            repository.delete(cita._id)
        }
        val citass = repository.findById("21")

        Assertions.assertAll(
            { Assertions.assertNotNull(citas) },
            { Assertions.assertNull(citass) }
        )
    }


}