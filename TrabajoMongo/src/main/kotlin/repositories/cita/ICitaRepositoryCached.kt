package repositories.cita

import models.Cita
import repositories.CrudRepository
import java.time.LocalDateTime

interface ICitaRepositoryCached : CrudRepository<Cita, String> {

}