package repositories.cita

import com.mongodb.client.model.Filters.*
import db.MongoDbManager
import exception.CitaException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Cita
import java.time.LocalDateTime

class CitaRepository : ICitaRepository {
    override suspend fun findAll(): Result<Flow<Cita>> {
        return try {
            val citas = MongoDbManager.database.getCollection<Cita>().find().publisher.asFlow()
            Result.success(citas)
        } catch (e: Exception) {
            Result.failure(CitaException("Error al obtener todas las citas"))
        }
    }

    override suspend fun findById(id: String): Result<Cita?> {
        return try {
            val cita = MongoDbManager.database.getCollection<Cita>().findOneById(id)
            Result.success(cita)
        } catch (e: Exception) {
            Result.failure(CitaException("Error al obtener la cita con ID: $id"))
        }
    }

    override suspend fun save(entity: Cita): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Cita>().save(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido insertar la cita $entity"))
        }
    }

    override suspend fun update(entity: Cita): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Cita>().updateOneById(entity._id, entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido actualizar la cita $entity"))
        }
    }

    override suspend fun delete(_id: String): Result<Unit> {
        return try {
            MongoDbManager.database.getCollection<Cita>().deleteOneById(_id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(CitaException("No se ha podido borrar la cita con el _id $_id"))
        }
    }

    override suspend fun findByTrabajadorAndIntervalo(trabajador: String, fechaHora: LocalDateTime): Result<List<Cita>> {
        return try {
            val query = and(
                eq("idTrabajador", trabajador),
                gte("fechaHora", fechaHora),
                lt("fechaHora", fechaHora.plusMinutes(30))
            )

            val citas = MongoDbManager.database.getCollection<Cita>().find(query).toList()
            Result.success(citas)
        } catch (e: Exception) {
            Result.failure(CitaException("Error al buscar las citas del trabajador $trabajador en el intervalo $fechaHora"))
        }
    }

    override suspend fun findByIntervalo(fechaHora: LocalDateTime): Result<List<Cita>> {
        return try {
            val intervaloInicio = fechaHora
            val intervaloFin = fechaHora.plusMinutes(30)

            val query = and(
                gte("fechaHora", intervaloInicio),
                lt("fechaHora", intervaloFin)
            )

            val citas = MongoDbManager.database.getCollection<Cita>().find(query).toList()
            Result.success(citas)
        } catch (e: Exception) {
            Result.failure(CitaException("Error al buscar las citas en el intervalo $fechaHora"))
        }
    }
}
