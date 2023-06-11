package repositories

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Informe
import models.Propietario
import models.Trabajador
import models.Vehiculo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.informe.InformeRepository
import repositories.propietario.PropietarioRepository
import repositories.trabajador.TrabajadorRepository
import repositories.vehiculo.VehiculoRepository
import java.time.LocalDate
import java.util.*

class InformeRepositoryTest {
    val repository = InformeRepository()
    val trabajadorRepository = TrabajadorRepository()
    val propietarioRrepository = PropietarioRepository()
    val vehiculoRepository = VehiculoRepository()


    val trabajador = Trabajador(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb5"),
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

    val vehiculo = Vehiculo(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb4"),
        marca = "Mercedes-Benz",
        modelo = "E-Class",
        matricula = "STU901",
        fechaMatriculacion = LocalDate.of(2021, 3, 12),
        fechaUltimaRevision = LocalDate.of(2022, 7, 20)
    )

    val propietario = Propietario(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb3"),
        dni = "12345678A",
        nombre = "Juan",
        apellidos = "Pérez López",
        teléfono = "123456789"
    )
    val entity = Informe(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb2"),
        frenado = 5,
        contaminación = 2.1,
        aptoFrenado = true,
        luces = true,
        apto = true,
        idTrabajador = trabajador,
        idVehiculo = vehiculo,
        idPropietario = propietario
    )


    @BeforeEach
    fun setUp(): Unit = runBlocking {
        repository.deleteAll()
    }

    @Test
    fun findAll(): Unit = runBlocking {
        val tr = trabajadorRepository.save(trabajador)
        val pr = propietarioRrepository.save(propietario)
        val vl = vehiculoRepository.save(vehiculo)

        val r = repository.save(entity)
        val list = repository.findAll().getOrNull()?.toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list?.size) }
        )
        repository.delete(entity.uuid)
        trabajadorRepository.delete(trabajador.uuid)
        propietarioRrepository.delete(propietario.uuid)
        vehiculoRepository.delete(vehiculo.uuid)

    }

    @Test
    fun findById() = runBlocking {
        val tr = trabajadorRepository.save(trabajador)
        val pr = propietarioRrepository.save(propietario)
        val vl = vehiculoRepository.save(vehiculo)

        val r = repository.save(entity)

        var encontrado = repository.findById(UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb2")).getOrNull()
        Assertions.assertAll(
            { Assertions.assertNotNull(encontrado) },
            { Assertions.assertEquals(entity.apto, encontrado?.apto) },
            { Assertions.assertEquals(entity?.contaminación,encontrado?.contaminación) },
            { Assertions.assertEquals(entity?.frenado,encontrado?.frenado) }

        )

    }

    @Test
    fun save(): Unit = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(entity.uuid).getOrNull()
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(entity.apto, entityy?.apto) },
            { Assertions.assertEquals(entity?.contaminación,entityy?.contaminación) },
            { Assertions.assertEquals(entity?.frenado,entityy?.frenado) }
        )
        repository.delete(entity.uuid)
    }

    @Test
    fun update(): Unit = runBlocking {
        val tr = repository.save(entity)
        repository.update(entity)
        val entityy = repository.findById(entity.uuid).getOrNull()
        Assertions.assertAll(
            { Assertions.assertEquals(entity.uuid, entity?.uuid) }
        )
        repository.delete(entity.uuid)

    }

    @Test
    fun delete() = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(entity.uuid).getOrNull()
        repository.delete(trabajador.uuid)

        val entityyy = repository.findById(entity.uuid).getOrNull()

        Assertions.assertNull(entityy)
        Assertions.assertNull(entityyy)

    }



}