package com.example.itvspringapplication.repositories.vehiculo

import com.example.itvspringapplication.models.Vehiculo
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface VehiculoRepository : CoroutineCrudRepository<Vehiculo,String> {
}