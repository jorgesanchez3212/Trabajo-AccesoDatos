package controllers


import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.informe.InformeRepository
import repositories.informe.InformeRepositoryCached
import repositories.propietario.PropietarioRepository
import repositories.trabajador.TrabajadorRepository
import repositories.vehiculo.VehiculoRepository
import repositories.vehiculo.VehiculoRepositoryCached
import services.informes.InformeCache
import services.vehiculos.VehiculosCache
import java.time.LocalDate
import java.util.*
import javax.persistence.Persistence
import javax.persistence.Query


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InformeControllerTest {


    var repository = InformeRepository()
    var repositoryCached = InformeRepositoryCached(InformeCache())
    var controller = InformeController(repository,repositoryCached)

    var repositoryVehiculo = VehiculoRepository()
    var repositoryCachedVehiculo = VehiculoRepositoryCached(VehiculosCache())
    var controllerVehiculo = VehiculoController(repositoryVehiculo,repositoryCachedVehiculo)

    var repositoryTrabajador = TrabajadorRepository()
    var controllerTrabajador = TrabajadorController(repositoryTrabajador)


    var repositoryPropietario = PropietarioRepository()
    var controllerPropietario = PropietarioController(repositoryPropietario)




    val trabajador = Trabajador(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        nombre = "Juan Pérez",
        telefono = 123456789,
        email = "juan1@gmail.com",
        username = "juan1perez",
        contraseña = "password123".toByteArray(),
        fechaContratacion = LocalDate.of(2020, 5, 15),
        especialidad = Trabajador.Especialidad.MOTOR.name,
        salario = 2800,
        responsable = true
    )


    val propietario = Propietario(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        dni = "67890123F",
        nombre = "MiAbuela",
        apellidos = "Gómez Ruiz",
        teléfono = "777777777"
    )

    val vehiculo = Vehiculo(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        marca = "Toyota",
        modelo = "Corolla",
        matricula = "ABC123",
        fechaMatriculacion = LocalDate.of(2022, 1, 15),
        fechaUltimaRevision = LocalDate.of(2022, 12, 30)
    )

    val entity =  Informe(
        uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
        frenado = 5,
        contaminación = 2.5,
        aptoFrenado = true,
        luces = true,
        apto = true,
        idTrabajador = trabajador,
        idVehiculo = vehiculo,
        idPropietario = propietario
    )

    fun borrarDatosTablas() {
        val emf = Persistence.createEntityManagerFactory("default")
        val em = emf.createEntityManager()
        val tx = em.transaction
        tx.begin()

        // Borrar datos de cada tabla
        val consultaInforme = "DELETE FROM Informe"
        val consultaPropietario = "DELETE FROM Propietario"
        val consultaTrabajador = "DELETE FROM Trabajador"
        val consultaVehiculo = "DELETE FROM Vehiculo"
        val queryInforme: Query = em.createQuery(consultaInforme)
        val queryPropietario: Query = em.createQuery(consultaPropietario)
        val queryTrabajador: Query = em.createQuery(consultaTrabajador)
        val queryVehiculo: Query = em.createQuery(consultaVehiculo)
        queryInforme.executeUpdate()
        queryPropietario.executeUpdate()
        queryTrabajador.executeUpdate()
        queryVehiculo.executeUpdate()
        tx.commit()
        em.close()
    }

    @Test
    fun findAll() = runTest {

        borrarDatosTablas()
        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var res = controller.findAllInforme()!!.toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        controller.borrarInforme(entity.uuid)
        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }


    @Test
    fun create() = runTest {
        borrarDatosTablas()
        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var entity1 = controller.findByIdInforme(entity.uuid)
        assertAll(
            { assertNotNull( entity1)}
        )
        controller.borrarInforme(entity.uuid)
        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }


    @Test
    fun findById() = runTest {
        borrarDatosTablas()
        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var entity1 = controller.findByIdInforme(entity.uuid)
        assertAll(
            { assertNotNull( entity1)},
            { assertEquals(entity, entity1) }
        )
        controller.borrarInforme(entity.uuid)
        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)


    }

    @Test
    fun update() = runTest {
        borrarDatosTablas()
        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)

        val entity1 =  Informe(
            uuid = UUID.fromString("8f121bdd-238a-4c59-a7e3-0c1f382aefb7"),
            frenado = 6,
            contaminación = 2.5,
            aptoFrenado = true,
            luces = true,
            apto = true,
            idTrabajador = trabajador,
            idVehiculo = vehiculo,
            idPropietario = propietario
        )
        controller.updateInforme(entity1)
        var en = controller.findByIdInforme(entity.uuid)
        assertAll(
            { assertNotEquals(en?.frenado, entity?.frenado) },
            { assertEquals(en?.contaminación, entity?.contaminación) }

        )

        controller.borrarInforme(entity.uuid)
        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }

    @Test
    fun delete() = runTest {

        borrarDatosTablas()
        controllerPropietario.savePropietario(propietario)
        controllerTrabajador.saveTrabajador(trabajador)
        controllerVehiculo.saveVehiculo(vehiculo)

        controller.saveInforme(entity)
        var en = controller.findByIdInforme(entity.uuid)
        assertAll(
            { assertNotNull(en) }
        )
        controller.borrarInforme(entity.uuid)
        var listBorrar = controller.findAllInforme()!!.toList()

        assertAll(
            { assertEquals(0,listBorrar.size) }
        )

        controllerPropietario.borrarPropietario(propietario.uuid)
        controllerTrabajador.borrarTrabajador(trabajador.uuid)
        controllerVehiculo.borrarVehiculo(vehiculo.uuid)
    }


}