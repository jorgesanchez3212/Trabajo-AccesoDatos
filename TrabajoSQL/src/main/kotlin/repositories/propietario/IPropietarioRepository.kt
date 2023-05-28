package repositories.propietario

import models.Propietario
import repositories.CrudRepository
import java.util.UUID

interface IPropietarioRepository : CrudRepository<Propietario, UUID> {
}