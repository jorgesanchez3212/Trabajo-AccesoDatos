package com.example.itvspringapplication.repositories.cita

import com.example.itvspringapplication.models.Cita
import kotlinx.coroutines.flow.Flow

interface ICitaRepositoryCached {
    suspend fun findAll(): Flow<Cita>
    suspend fun findById(id: String): Cita?
    suspend fun save(entity: Cita)
    suspend fun update(entity: Cita)
    suspend fun delete(_id: String)
}