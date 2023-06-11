package repositories.trabajador

import models.Trabajador
import repositories.CrudRepository
import java.util.UUID

interface ITrabajadorRepository : CrudRepository<Trabajador, UUID> {
    suspend fun findByEmail(email: String): Result<Trabajador?>
    suspend fun findByUsername(username: String): Result<Trabajador?>


}