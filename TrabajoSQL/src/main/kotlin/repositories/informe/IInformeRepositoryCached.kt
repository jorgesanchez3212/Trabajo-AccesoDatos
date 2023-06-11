package repositories.informe

import models.Informe
import repositories.CrudRepositoryCached
import java.util.*

interface IInformeRepositoryCached  : CrudRepositoryCached<Informe, UUID> {
}