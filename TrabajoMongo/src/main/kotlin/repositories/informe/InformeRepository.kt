package repositories.informe

import db.MongoDbManager
import exception.InformeException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Informe


class InformeRepository : IInformeRepository {
    override suspend fun findAll(): Result<Flow<Informe>> {
        return try {
            val informes = MongoDbManager.database.getCollection<Informe>().find().publisher.asFlow()
            Result.success(informes)
        } catch (e: Exception) {
            Result.failure(InformeException("Error al obtener todos los informes"))
        }
    }

    override suspend fun findById(id: String): Result<Informe?> {
        return try {
            val informe = MongoDbManager.database.getCollection<Informe>().findOneById(id)
            Result.success(informe)
        } catch (e: Exception) {
            Result.failure(InformeException("Error al obtener el informe con ID: $id"))
        }
    }

    override suspend fun save(entity: Informe): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Informe>().save(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido insertar el informe $entity"))
        }
    }

    override suspend fun update(entity: Informe): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Informe>().updateOneById(entity._id, entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido actualizar el informe $entity"))
        }
    }

    override suspend fun delete(_id: String): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Informe>().deleteOneById(_id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(InformeException("No se ha podido borrar el informe con el _id $_id"))
        }
    }
}
