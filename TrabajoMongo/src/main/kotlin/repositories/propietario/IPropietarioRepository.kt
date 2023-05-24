package repositories.propietario

import models.Propietario
import repositories.CrudRepository

interface IPropietarioRepository : CrudRepository<Propietario, String> {
}