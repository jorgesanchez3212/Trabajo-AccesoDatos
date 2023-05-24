package repositories.vehiculo

import db.MongoDbManager
import exception.CitaException
import exception.VehiculoException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Vehiculo

class VehiculoRepository : IVehiculoRepository {
    override suspend fun findAll(): Flow<Vehiculo> {
        return MongoDbManager.database.getCollection<Vehiculo>().find().publisher.asFlow()
    }

    override suspend fun findById(id: String): Vehiculo? {
        return MongoDbManager.database.getCollection<Vehiculo>().findOneById(id)
    }

    override suspend fun save(entity: Vehiculo) {
        try {
            MongoDbManager.database.getCollection<Vehiculo>().save(entity)
        }catch(e: Exception) {
            throw VehiculoException("No se ha podido insertar el vehiculo $entity")
        }    }

    override suspend fun update(entity: Vehiculo) {
        try {
            MongoDbManager.database.getCollection<Vehiculo>().updateOneById(entity._id, entity)
        }catch(e: Exception) {
            throw CitaException("No se ha podido actualizar el vehiculo $entity")
        }    }

    override suspend fun delete(_id: String) {
        try {
            MongoDbManager.database.getCollection<Vehiculo>().deleteOneById(_id)
        }catch(e: Exception) {
            throw CitaException("No se ha podido borrar el vehiculo con el _id $_id")
        }    }

}