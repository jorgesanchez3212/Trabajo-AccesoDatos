package com.example.itvspringapplication.repositories.cita

import com.example.itvspringapplication.models.Cita
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CitaRepository : CoroutineCrudRepository<Cita,String> {
}