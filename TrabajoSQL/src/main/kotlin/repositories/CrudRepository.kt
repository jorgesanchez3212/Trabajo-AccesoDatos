package repositories

import kotlinx.coroutines.flow.Flow


interface CrudRepository<T,ID> {
    suspend fun findAll(): Result<Flow<T>>
    suspend fun findById(id: ID): Result<T?>
    suspend fun save(entity: T) : Result<Unit>
    suspend fun update(entity: T) : Result<Unit>
    suspend fun delete(_id: ID): Result<Unit>
}