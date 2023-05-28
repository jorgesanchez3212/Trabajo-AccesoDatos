package repositories.cita

import models.Cita
import models.Vehiculo
import repositories.CrudRepository
import java.util.*

interface ICitaRepository : CrudRepository<Cita, UUID> {
}