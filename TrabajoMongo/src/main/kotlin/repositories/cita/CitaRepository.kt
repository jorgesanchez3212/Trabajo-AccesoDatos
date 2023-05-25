package repositories.cita

import com.mongodb.client.model.Filters.*
import db.MongoDbManager
import exception.CitaException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Cita
import java.time.LocalDateTime

class CitaRepository : ICitaRepository {
    override suspend fun findAll(): Flow<Cita> {
        return MongoDbManager.database.getCollection<Cita>().find().publisher.asFlow()
    }

    override suspend fun findById(id: String): Cita? {
        return MongoDbManager.database.getCollection<Cita>().findOneById(id)
    }

    override suspend fun save(entity: Cita) {
        try {
            MongoDbManager.database.getCollection<Cita>().save(entity)
        }catch(e: Exception) {
            throw CitaException("No se ha podido insertar la cita $entity")
        }
    }

    override suspend fun update(entity: Cita) {
        try {
            MongoDbManager.database.getCollection<Cita>().updateOneById(entity._id, entity)
        }catch(e: Exception) {
            throw CitaException("No se ha podido actualizar la cita $entity")
        }
    }

    override suspend fun delete(_id: String) {
        try {
            MongoDbManager.database.getCollection<Cita>().deleteOneById(_id)
        }catch(e: Exception) {
            throw CitaException("No se ha podido borrar la cita con el _id $_id")
        }
    }

    override suspend fun findByTrabajadorAndIntervalo(trabajador: String, fechaHora: LocalDateTime): List<Cita> {
        val query = and(
            eq("idTrabajador", trabajador),
            gte("fechaHora", fechaHora),
            lt("fechaHora", fechaHora.plusMinutes(30))
        )

        return MongoDbManager.database.getCollection<Cita>().find(query).toList()
    }

    override suspend fun findByIntervalo(fechaHora: LocalDateTime): List<Cita> {
        val intervaloInicio = fechaHora
        val intervaloFin = fechaHora.plusMinutes(30)

        val query = and(
            gte("fechaHora", intervaloInicio),
            lt("fechaHora", intervaloFin)
        )

        return MongoDbManager.database.getCollection<Cita>().find(query).toList()
    }


}