package repositories.vehiculo

import models.Vehiculo
import repositories.CrudRepository
import java.util.UUID

interface IVehiculoRepository : CrudRepository<Vehiculo, UUID> {
}