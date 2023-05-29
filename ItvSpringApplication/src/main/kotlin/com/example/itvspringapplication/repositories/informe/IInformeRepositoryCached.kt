package com.example.itvspringapplication.repositories.informe

import com.example.itvspringapplication.models.Informe
import kotlinx.coroutines.flow.Flow

interface IInformeRepositoryCached {
    suspend fun findAll(): Flow<Informe>
    suspend fun findById(id: String): Informe?
    suspend fun save(entity: Informe)
    suspend fun update(entity: Informe)
    suspend fun delete(_id: String)
}