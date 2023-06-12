package repositories.trabajador

import db.MongoDbManager
import exception.TrabajadorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Trabajador
import org.litote.kmongo.eq


class TrabajadorRepository : ITrabajadorRepository {
    override suspend fun findAll(): Result<Flow<Trabajador>> {
        return try {
            val trabajadores = MongoDbManager.database.getCollection<Trabajador>().find().publisher.asFlow()
            Result.success(trabajadores)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("Error al obtener todos los trabajadores"))
        }
    }

    override suspend fun findById(id: String): Result<Trabajador?> {
        return try {
            val trabajador = MongoDbManager.database.getCollection<Trabajador>().findOneById(id)
            Result.success(trabajador)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("Error al obtener el trabajador con ID: $id"))
        }
    }

    override suspend fun findByEmail(email: String): Result<Trabajador?> {
        return try {
            val trabajador = MongoDbManager.database.getCollection<Trabajador>().findOne(Trabajador::email eq email)
            Result.success(trabajador)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("Error al obtener el trabajador con email: $email"))
        }
    }

    override suspend fun findByUsername(username: String): Result<Trabajador?> {
        return try {
            val trabajador = MongoDbManager.database.getCollection<Trabajador>().findOne(Trabajador::username eq username)
            Result.success(trabajador)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("Error al obtener el trabajador con username: $username"))
        }
    }

    override suspend fun save(entity: Trabajador): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Trabajador>().save(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido insertar el trabajador $entity"))
        }
    }

    override suspend fun update(entity: Trabajador): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Trabajador>().updateOneById(entity._id, entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido actualizar el trabajador $entity"))
        }
    }

    override suspend fun delete(_id: String): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Trabajador>().deleteOneById(_id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(TrabajadorException("No se ha podido borrar el trabajador con el _id $_id"))
        }
    }
}
