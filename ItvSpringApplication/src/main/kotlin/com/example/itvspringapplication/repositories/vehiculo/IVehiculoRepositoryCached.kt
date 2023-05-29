package com.example.itvspringapplication.repositories.vehiculo

import com.example.itvspringapplication.models.Vehiculo
import kotlinx.coroutines.flow.Flow

interface IVehiculoRepositoryCached {
    suspend fun findAll(): Flow<Vehiculo>
    suspend fun findById(id: String): Vehiculo?
    suspend fun save(entity: Vehiculo)
    suspend fun update(entity: Vehiculo)
    suspend fun delete(_id: String)
}