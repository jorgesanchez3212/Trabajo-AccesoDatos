import controllers.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import repositories.cita.CitaRepository
import repositories.cita.CitaRepositoryCached
import repositories.informe.InformeRepository
import repositories.informe.InformeRepositoryCached
import repositories.propietario.PropietarioRepository
import repositories.trabajador.TrabajadorRepository
import repositories.vehiculo.VehiculoRepository
import repositories.vehiculo.VehiculoRepositoryCached
import services.citas.CitaCache
import services.informes.InformeCache
import services.vehiculos.VehiculosCache
import view.ItvView


fun main() = runBlocking {
    val cita = CitaController(CitaRepository(), CitaRepositoryCached(CitaCache()))

    val itv = ItvView(cita,
        InformeController(InformeRepository(), InformeRepositoryCached(InformeCache())),
        PropietarioController(PropietarioRepository()),
        TrabajadorController(TrabajadorRepository()),
        VehiculoController(VehiculoRepository(), VehiculoRepositoryCached(VehiculosCache()))
    )

    CoroutineScope(Dispatchers.IO).launch {
        cita.state.collect{
            println("SE HA PRODUCIDO CAMBIO EN CITAS -> $it")
        }
    }

    itv.añadirDatos()
    itv.informes()


    System.exit(0)

}

