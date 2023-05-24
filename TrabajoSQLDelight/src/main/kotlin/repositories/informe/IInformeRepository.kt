package repositories.informe

import models.Informe
import repositories.CrudRepository

interface IInformeRepository : CrudRepository<Informe, Int> {
}