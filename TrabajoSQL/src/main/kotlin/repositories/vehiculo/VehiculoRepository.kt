package repositories.vehiculo

import db.HibernateManager
import exception.VehiculoException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Vehiculo
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

private var logger = KotlinLogging.logger {  }


class VehiculoRepository : IVehiculoRepository {

    override suspend fun findAll(): Result<Flow<Vehiculo>> {
        logger.info { "Buscando todos los vehiculos" }
        return try {
            var lista = mutableListOf<Vehiculo>()
            HibernateManager.query {
                var query : TypedQuery<Vehiculo> = HibernateManager.manager.createNamedQuery("Vehiculo.findAll",
                    Vehiculo::class.java)
                lista = query.resultList
            }
            return Result.success(lista.asFlow())
        }catch (e : Exception){
            Result.failure(VehiculoException("No se ha podido obtener todos los vehículos"))

        }

    }

    override suspend fun findById(id: UUID): Result<Vehiculo?> {
        logger.info { "Buscando el vehiculo con id $id" }
        return try {
            var encontrado : Vehiculo? = null
            HibernateManager.query {
                encontrado = HibernateManager.manager.find(Vehiculo::class.java, id)
            }
            Result.success(encontrado)
        }catch (e: Exception){
            Result.failure(VehiculoException("No se ha podido encontrar el vehículo con el ID $id"))
        }

    }

    override suspend fun save(entity: Vehiculo) : Result<Unit> {
        logger.info { "Insertando el vehiculo $entity" }
        return try {
            var vehiculo : Vehiculo? = null
            HibernateManager.transaction {
                vehiculo = HibernateManager.manager.merge(entity)
            }
            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(VehiculoException("No se ha podido insertar el vehículo $entity"))
        }

    }

    override suspend fun update(entity: Vehiculo) : Result<Unit> {
        logger.info { "Actualizar el vehiculo $entity"}

        return try {
            HibernateManager.transaction {
                var vehiculoExisting = HibernateManager.manager.find(Vehiculo::class.java,entity.uuid)
                if (vehiculoExisting != null){
                    vehiculoExisting.marca = entity.marca
                    vehiculoExisting.modelo = entity.modelo
                    vehiculoExisting.matricula = entity.matricula
                    vehiculoExisting.fechaMatriculacion = entity.fechaMatriculacion
                    vehiculoExisting.fechaUltimaRevision = entity.fechaUltimaRevision


                    HibernateManager.manager.merge(vehiculoExisting)
                    logger.info { "Vehiculo actualizado correctamente" }
                    Result.success(Unit)
                }else{
                    logger.info { "Vehiculo no encontrado" }
                    Result.failure(VehiculoException("Vehiculo no encontrado"))
                }
            }
            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(VehiculoException("No se ha podido actualizar el vehículo $entity"))
        }

    }

    override suspend fun delete(_id: UUID) : Result<Unit> {
        logger.info { "Eliminando vehiculo" }
        return try{

            var vehiculo : Vehiculo? = null
            HibernateManager.transaction {
                vehiculo = HibernateManager.manager.find(Vehiculo::class.java, _id)
                if (vehiculo != null) {
                    HibernateManager.manager.remove(vehiculo)
                    logger.info { "Vehiculo eliminado correctamente" }
                    Result.success(Unit)
                } else {
                    logger.info { "Vehiculo no encontrado" }
                    Result.failure(VehiculoException("No se ha podido borrar el vehículo $_id"))
                }
            }
            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(VehiculoException("No se ha podido borrar el vehículo $_id"))

        }
    }

    fun deleteAll() : Result<Unit> {
        return try {
            HibernateManager.transaction {
                var query = HibernateManager.manager.createQuery("delete from Vehiculo ")
                query.executeUpdate()
            }
            Result.success(Unit)
        }catch(e: Exception){
            Result.failure(VehiculoException("No se ha podido borrar los vehículos"))
        }

    }
}