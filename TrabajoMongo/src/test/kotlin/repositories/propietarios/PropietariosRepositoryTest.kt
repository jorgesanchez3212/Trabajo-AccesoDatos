package repositories.propietarios

import db.MongoDbManager
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Propietario
import models.Trabajador
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.propietario.PropietarioRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropietariosRepositoryTest {

    val repository = PropietarioRepository()


    val propietario =  Propietario(
        _id = "21",
        dni = "12345678A",
        nombre = "Arturo",
        apellidos = "Pérez López",
        teléfono = "1234516789"
    )


    //Test del findAll de propietario
    @Test
    fun findAll()  = runBlocking {
        MongoDbManager.database.getCollection<Propietario>().drop()
        repository.save(propietario)
        val list = repository.findAll().toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list.size) }
        )
        repository.delete(propietario._id)
    }

    // Test del findById de propietario
    @Test
    fun findById() = runBlocking {
        repository.save(propietario)
        val propietarios = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(propietario) },
            { Assertions.assertEquals("21", propietario._id) }
        )
        repository.delete(propietario._id)
    }

    // Test del save de propietario
    @Test
    fun save() = runBlocking {
        repository.save(propietario)
        val propietarios = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(propietario) },
            { Assertions.assertEquals("21", propietario._id) }
        )
        repository.delete(propietario._id)
    }

    // Test del update de propietario
    @Test
    fun update() = runBlocking {
        repository.save(propietario)
        repository.update(propietario)
        val propietarios = repository.findById("21")
        Assertions.assertAll(
            { Assertions.assertNotNull(propietario) },
            { Assertions.assertEquals("21", propietario._id) }
        )
        repository.delete(propietario._id)

    }

    // Test del delete de propietario
    @Test
    fun delete() = runBlocking {
        repository.save(propietario)
        val propietarioo = repository.findById("21")
        if(propietarioo != null){
            repository.delete(propietario._id)
        }
        val propietariooo = repository.findById("21")

        Assertions.assertAll(
            { Assertions.assertNotNull(propietarioo) },
            { Assertions.assertNull(propietariooo) }
        )
    }

}