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

class InformeRepositoryTest {
    val repository = InformeRepository()
    val trabajadorRepository = TrabajadorRepository()
    val propietarioRrepository = PropietarioRepository()
    val vehiculoRepository = VehiculoRepository()


    val trabajador = Trabajador(
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
        marca = "Mercedes-Benz",
        modelo = "E-Class",
        matricula = "STU901",
        fechaMatriculacion = LocalDate.of(2021, 3, 12),
        fechaUltimaRevision = LocalDate.of(2022, 7, 20)
    )

    val propietario = Propietario(
        dni = "12345678A",
        nombre = "Juan",
        apellidos = "Pérez López",
        teléfono = "123456789"
    )
    val entity = Informe(
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
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun findAll()  = runBlocking {
        val tr = trabajadorRepository.save(trabajador)
        val pr = propietarioRrepository.save(propietario)
        val vl = vehiculoRepository.save(vehiculo)

        val r = repository.save(entity)
        val list = repository.findAll().toList()
        Assertions.assertAll(
            { Assertions.assertNotNull(list) },
            { Assertions.assertEquals(1, list.size) }
        )
        repository.delete(r.uuid)
        trabajadorRepository.delete(tr.uuid)
        propietarioRrepository.delete(pr.uuid)
        vehiculoRepository.delete(vl.uuid)

    }

    @Test
    fun findById() = runBlocking {
        val tr = repository.save(entity)
        var encontrado = repository.findById(tr.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(encontrado) },
            { Assertions.assertEquals(tr.apto, encontrado?.apto) },
            { Assertions.assertEquals(tr?.contaminación,encontrado?.contaminación) },
            { Assertions.assertEquals(tr?.frenado,encontrado?.frenado) }

        )

    }

    @Test
    fun save() = runBlocking {
        val tr = repository.save(entity)
        val entityy = repository.findById(tr.uuid)
        Assertions.assertAll(
            { Assertions.assertNotNull(entityy) },
            { Assertions.assertEquals(tr.apto, entityy?.apto) },
            { Assertions.assertEquals(tr?.contaminación,entityy?.contaminación) },
            { Assertions.assertEquals(tr?.frenado,entityy?.frenado) }
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