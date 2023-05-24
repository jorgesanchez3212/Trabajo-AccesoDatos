package repositories.trabajador

import db.MongoDbManager
import exception.CitaException
import exception.TrabajadorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Trabajador

class TrabajadorRepository : ITrabajadorRepository {
    override suspend fun findAll(): Flow<Trabajador> {
        return MongoDbManager.database.getCollection<Trabajador>().find().publisher.asFlow()
    }

    override suspend fun findById(id: String): Trabajador? {
        return MongoDbManager.database.getCollection<Trabajador>().findOneById(id)
    }

    override suspend fun findByEmail(email: String): Trabajador? {
        return MongoDbManager.database.getCollection<Trabajador>().findOne(email)
    }

    override suspend fun findByUsername(username: String): Trabajador? {
        return MongoDbManager.database.getCollection<Trabajador>().findOne(username)
    }

    override suspend fun save(entity: Trabajador) {
        try {
            MongoDbManager.database.getCollection<Trabajador>().save(entity)
        }catch(e: Exception) {
            throw TrabajadorException("No se ha podido insertar el trabajador $entity")
        }    }

    override suspend fun update(entity: Trabajador) {
        try {
            MongoDbManager.database.getCollection<Trabajador>().updateOneById(entity._id, entity)
        }catch(e: Exception) {
            throw CitaException("No se ha podido actualizar el trabajador $entity")
        }
    }

    override suspend fun delete(_id: String) {
        try {
            MongoDbManager.database.getCollection<Trabajador>().deleteOneById(_id)
        }catch(e: Exception) {
            throw CitaException("No se ha podido borrar el trabajador con el _id $_id")
        }    }

}