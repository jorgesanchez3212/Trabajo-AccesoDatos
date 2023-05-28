package repositories.trabajador

import models.Trabajador
import repositories.CrudRepository
import java.util.UUID

interface ITrabajadorRepository : CrudRepository<Trabajador, UUID> {
    suspend fun findByEmail(email: String): Trabajador?
    suspend fun findByUsername(username: String): Trabajador?


}