package repositories

import kotlinx.coroutines.flow.Flow


interface CrudRepository<T,ID> {
    suspend fun findAll(): Flow<T>
    suspend fun findById(id: ID): T?
    suspend fun save(entity: T) : T
    suspend fun update(entity: T)
    suspend fun delete(_id: ID)
}