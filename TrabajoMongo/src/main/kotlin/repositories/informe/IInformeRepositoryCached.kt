package repositories.informe

import models.Informe
import repositories.CrudRepositoryCached

interface IInformeRepositoryCached : CrudRepositoryCached<Informe,String> {
}