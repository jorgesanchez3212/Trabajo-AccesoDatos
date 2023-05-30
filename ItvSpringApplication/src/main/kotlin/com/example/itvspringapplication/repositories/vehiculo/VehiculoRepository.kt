package com.example.itvspringapplication.repositories.vehiculo

import com.example.itvspringapplication.models.Vehiculo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VehiculoRepository : CoroutineCrudRepository<Vehiculo,String> {
}