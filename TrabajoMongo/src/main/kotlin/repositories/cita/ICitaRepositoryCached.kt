package repositories.cita

import models.Cita
import repositories.CrudRepository
import repositories.CrudRepositoryCached
import java.time.LocalDateTime

interface ICitaRepositoryCached : CrudRepositoryCached<Cita, String> {

}