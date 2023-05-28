package repositories.cita

import models.Cita
import repositories.CrudRepository
import java.util.UUID

interface ICitaRepositoryCached : CrudRepository<Cita,UUID> {
}