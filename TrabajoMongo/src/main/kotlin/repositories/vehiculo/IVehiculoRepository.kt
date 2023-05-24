package repositories.vehiculo

import models.Vehiculo
import repositories.CrudRepository

interface IVehiculoRepository : CrudRepository<Vehiculo, String> {
}