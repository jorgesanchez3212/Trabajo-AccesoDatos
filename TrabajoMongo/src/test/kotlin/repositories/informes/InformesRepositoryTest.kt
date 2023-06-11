package repositories.informes

import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Informe
import models.Trabajador
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.informe.InformeRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InformesRepositoryTest {

    val repository  = InformeRepository()

    val informe =  Informe(
        _id = "21",
        frenado = 5,
        contaminación = 2.1,
        aptoFrenado = true,
        luces = true,
        apto = true,
        idTrabajador = "1",
        idVehiculo = "1",
        idPropietario = "1"
    )


    //Test del findAll de informe
    @Test
    fun findAll(): Unit = runBlocking {
        MongoDbManager.database.getCollection<Informe>().drop()
        repository.save(informe)
        val list = repository.findAll().getOrNull()!!.toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list.size) }
        )
        repository.delete(informe._id)
    }

    // Test del findById de informe
    @Test
    fun findById(): Unit = runBlocking {
        repository.save(informe)
        val informes = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(informe) },
            { Assertions.assertEquals("21", informe._id) }
        )
        repository.delete(informe._id)
    }

    // Test del save de informe
    @Test
    fun save(): Unit = runBlocking {
        repository.save(informe)
        val informes = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(informe) },
            { Assertions.assertEquals("21", informe._id) }
        )
        repository.delete(informe._id)
    }

    // Test del update de informe
    @Test
    fun update(): Unit = runBlocking {
        repository.save(informe)
        repository.update(informe)
        val informes = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(informe) },
            { Assertions.assertEquals("21", informe._id) }
        )
        repository.delete(informe._id)

    }

    // Test del delete de informe
    @Test
    fun delete() = runBlocking {
        repository.save(informe)
        val informes = repository.findById("21").getOrNull()
        if(informes != null){
            repository.delete(informe._id)
        }
        val informess = repository.findById("21").getOrNull()

        Assertions.assertAll(
            { Assertions.assertNotNull(informes) },
            { Assertions.assertNull(informess) }
        )
    }

}