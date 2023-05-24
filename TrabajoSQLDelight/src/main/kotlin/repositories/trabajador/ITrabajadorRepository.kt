package repositories.trabajador

import models.Trabajador
import repositories.CrudRepository

interface ITrabajadorRepository : CrudRepository<Trabajador, Int> {
}