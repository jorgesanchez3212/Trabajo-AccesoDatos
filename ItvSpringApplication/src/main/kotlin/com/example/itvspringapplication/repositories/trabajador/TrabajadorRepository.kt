package com.example.itvspringapplication.repositories.trabajador

import com.example.itvspringapplication.models.Trabajador
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TrabajadorRepository : CoroutineCrudRepository<Trabajador, String> {
    suspend fun findTrabajadorByEmail(email:String):Trabajador?
    suspend fun findTrabajadorByUsername(username:String):Trabajador?

}