package repositories.propietario

import db.MongoDbManager
import exception.CitaException
import exception.PropietarioException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Propietario

class PropietarioRepository : IPropietarioRepository {
    override suspend fun findAll(): Flow<Propietario> {
        return MongoDbManager.database.getCollection<Propietario>().find().publisher.asFlow()
    }

    override suspend fun findById(id: String): Propietario? {
        return MongoDbManager.database.getCollection<Propietario>().findOneById(id)
    }

    override suspend fun save(entity: Propietario) {
        try {
            MongoDbManager.database.getCollection<Propietario>().save(entity)
        }catch(e: Exception) {
            throw PropietarioException("No se ha podido insertar el propietario $entity")
        }
    }

    override suspend fun update(entity: Propietario) {
        try {
            MongoDbManager.database.getCollection<Propietario>().updateOneById(entity._id, entity)
        }catch(e: Exception) {
            throw CitaException("No se ha podido actualizar el propietario $entity")
        }
    }

    override suspend fun delete(_id: String) {
        try {
            MongoDbManager.database.getCollection<Propietario>().deleteOneById(_id)
        }catch(e: Exception) {
            throw CitaException("No se ha podido borrar el propietario con el _id $_id")
        }    }

}