package repositories.informe

import models.Informe
import repositories.CrudRepository
import java.util.UUID

interface IInformeRepository : CrudRepository<Informe, UUID> {
}