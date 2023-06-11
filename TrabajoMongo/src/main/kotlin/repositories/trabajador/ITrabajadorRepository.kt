package repositories.trabajador

import models.Trabajador
import repositories.CrudRepository

interface ITrabajadorRepository : CrudRepository<Trabajador, String> {
    suspend fun findByEmail(email: String): Result<Trabajador?>
    suspend fun findByUsername(username: String): Result<Trabajador?>


}