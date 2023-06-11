package repositories.propietario

import db.MongoDbManager
import exception.CitaException
import exception.PropietarioException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Propietario

class PropietarioRepository : IPropietarioRepository {
    override suspend fun findAll(): Result<Flow<Propietario>> {
        return try {
            val propietarios = MongoDbManager.database.getCollection<Propietario>().find().publisher.asFlow()
            Result.success(propietarios)
        } catch (e: Exception) {
            Result.failure(PropietarioException("Error al obtener todos los propietarios"))
        }
    }

    override suspend fun findById(id: String): Result<Propietario?> {
        return try {
            val propietario = MongoDbManager.database.getCollection<Propietario>().findOneById(id)
            Result.success(propietario)
        } catch (e: Exception) {
            Result.failure(PropietarioException("Error al obtener el propietario con ID: $id"))
        }
    }

    override suspend fun save(entity: Propietario): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Propietario>().save(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido insertar el propietario $entity"))
        }
    }

    override suspend fun update(entity: Propietario): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Propietario>().updateOneById(entity._id, entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido actualizar el propietario $entity"))
        }
    }

    override suspend fun delete(_id: String): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Propietario>().deleteOneById(_id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(PropietarioException("No se ha podido borrar el propietario con el _id $_id"))
        }
    }
}
