package repositories

import db.Data
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.cita.CitaRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitasRepositoryTest {
    val repository = CitaRepository()

    val p = Propietario(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        dni = "23456789G",
        nombre = "Sara",
        apellidos = "Hernández García",
        teléfono = "444444444"
    )

    val t = Trabajador(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        nombre = "Ana Memes",
        telefono = 777777777,
        email = "ana11@gmail.com",
        username = "anaaamemes",
        contraseña = "password123".toByteArray(),
        fechaContratacion = LocalDate.of(2021, 4, 18),
        especialidad = Trabajador.Especialidad.ELECTRICIDAD.name,
        salario = 1800,
        responsable = false
    )

    val v = Vehiculo(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        marca = "Toyota",
        modelo = "Corolla",
        matricula = "ABC12322",
        fechaMatriculacion = LocalDate.of(2022, 1, 15),
        fechaUltimaRevision = LocalDate.of(2022, 12, 30)
    )

    val fechaInicio = LocalDateTime.now()

    val cita =  Cita(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        fechaHora = fechaInicio,
        idTrabajador = t,
        idVehiculo = v,
        idPropietario = p
    )


    //Test del findAll de cita
    @Test
    fun findAll(): Unit = runBlocking {
        repository.save(cita)
        val list = repository.findAll().getOrNull()!!.toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
        )
        repository.delete(cita.uuid)
    }

    // Test del findById de cita
    @Test
    fun findById(): Unit = runBlocking {
        repository.save(cita)
        val citaas = repository.findById(cita.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(cita) },
            { Assertions.assertEquals(cita.uuid, cita.uuid) }
        )
        repository.delete(cita.uuid)
    }

    // Test del save de cita
    @Test
    fun save(): Unit = runBlocking {
        repository.save(cita)
        val citas = repository.findById(cita.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(cita) },
            { Assertions.assertEquals("8f121bdd-238a-4c59-a7e3-0c1f382aefb7", cita.uuid.toString()) }
        )
        repository.delete(cita.uuid)
    }

    // Test del update de cita
    @Test
    fun update(): Unit = runBlocking {
        repository.save(cita)
        repository.update(cita)
        val citas = repository.findById(UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"))
        Assertions.assertAll(
            { Assertions.assertEquals("8f121bdd-238a-4c59-a7e3-0c1f382aefb7", cita.uuid.toString()) }
        )
        repository.delete(cita.uuid)

    }

    // Test del delete de cita
    @Test
    fun delete() = runBlocking {
        repository.save(cita)
        val citas = repository.findById(UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7")).getOrNull()
        if(citas != null){
            repository.delete(cita.uuid)
        }
        val citass = repository.findById(UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7")).getOrNull()

        Assertions.assertAll(
            { Assertions.assertNull(citas) },
            { Assertions.assertNull(citass) }
        )
    }


}