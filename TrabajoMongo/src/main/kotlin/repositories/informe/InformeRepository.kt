package repositories.informe

import db.MongoDbManager
import exception.CitaException
import exception.InformeException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Informe

class InformeRepository : IInformeRepository {
    override suspend fun findAll(): Flow<Informe> {
        return MongoDbManager.database.getCollection<Informe>().find().publisher.asFlow()
    }

    override suspend fun findById(id: String): Informe? {
        return MongoDbManager.database.getCollection<Informe>().findOneById(id)
    }

    override suspend fun save(entity: Informe) {
        try {
            MongoDbManager.database.getCollection<Informe>().save(entity)
        }catch(e: Exception) {
            throw InformeException("No se ha podido insertar el informe $entity")
        }
    }

    override suspend fun update(entity: Informe) {
        try {
            MongoDbManager.database.getCollection<Informe>().updateOneById(entity._id, entity)
        }catch(e: Exception) {
            throw CitaException("No se ha podido actualizar el informe $entity")
        }
    }

    override suspend fun delete(_id: String) {
        try {
            MongoDbManager.database.getCollection<Informe>().deleteOneById(_id)
        }catch(e: Exception) {
            throw CitaException("No se ha podido borrar el informe con el _id $_id")
        }    }

}