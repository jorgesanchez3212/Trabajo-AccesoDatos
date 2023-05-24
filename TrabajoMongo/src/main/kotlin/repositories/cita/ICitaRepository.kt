package repositories.cita

import models.Cita
import repositories.CrudRepository

interface ICitaRepository : CrudRepository<Cita,String> {
}