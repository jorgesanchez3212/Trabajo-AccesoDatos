import controllers.*
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
    val itv = ItvView(CitaController(CitaRepository(), CitaRepositoryCached(CitaCache())),
        InformeController(InformeRepository(), InformeRepositoryCached(InformeCache())),
        PropietarioController(PropietarioRepository()),
        TrabajadorController(TrabajadorRepository()),
        VehiculoController(VehiculoRepository(), VehiculoRepositoryCached(VehiculosCache()))
    )

    itv.añadirDatos()
    itv.informes()

}

