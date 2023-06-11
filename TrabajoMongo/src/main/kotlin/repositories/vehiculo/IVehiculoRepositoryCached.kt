package repositories.vehiculo

import models.Vehiculo
import repositories.CrudRepositoryCached

interface IVehiculoRepositoryCached : CrudRepositoryCached<Vehiculo,String> {
}