package repositories

import kotlinx.coroutines.flow.Flow

interface CrudRepositoryCached<T,ID> {

suspend fun findAll(): Flow<T>
suspend fun findById(id: ID): T?
suspend fun save(entity: T)
suspend fun update(entity: T)
suspend fun delete(_id: ID)


}