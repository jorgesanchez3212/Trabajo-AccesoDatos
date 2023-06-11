package repositories.cita

import models.Cita
import repositories.CrudRepositoryCached
import java.util.UUID

interface ICitaRepositoryCached  : CrudRepositoryCached<Cita, UUID> {
}