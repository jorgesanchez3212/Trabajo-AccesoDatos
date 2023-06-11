package repositories.vehiculo

import models.Vehiculo
import repositories.CrudRepositoryCached
import java.util.UUID

interface IVehiculoRepositoryCached : CrudRepositoryCached<Vehiculo,UUID> {
}