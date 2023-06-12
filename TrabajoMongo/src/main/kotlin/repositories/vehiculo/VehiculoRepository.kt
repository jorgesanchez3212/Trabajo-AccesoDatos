package repositories.vehiculo

import db.MongoDbManager
import exception.VehiculoException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Vehiculo

class VehiculoRepository : IVehiculoRepository {

    override suspend fun findAll(): Result<Flow<Vehiculo>>{
        return try {
            val flow = MongoDbManager.database.getCollection<Vehiculo>().find().publisher.asFlow()
            Result.success(flow)
        } catch (e: Exception) {
            Result.failure(VehiculoException("No se ha podido obtener todos los vehículos"))
        }
    }

    override suspend fun findById(id: String): Result<Vehiculo?> {
        return try {
            val vehiculo = MongoDbManager.database.getCollection<Vehiculo>().findOneById(id)
            Result.success(vehiculo)
        } catch (e: Exception) {
            Result.failure(VehiculoException("No se ha podido encontrar el vehículo con el ID $id"))
        }
    }

    override suspend fun save(entity: Vehiculo): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Vehiculo>().save(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(VehiculoException("No se ha podido insertar el vehículo $entity"))
        }
    }

    override suspend fun update(entity: Vehiculo): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Vehiculo>().updateOneById(entity._id, entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(VehiculoException("No se ha podido actualizar el vehículo $entity"))
        }
    }

    override suspend fun delete(_id: String): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Vehiculo>().deleteOneById(_id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(VehiculoException("No se ha podido borrar el vehículo con el ID $_id"))
        }
    }
}