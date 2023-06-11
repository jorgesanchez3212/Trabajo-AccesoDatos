package com.example.itvspringapplication.repositories.cita

import com.example.itvspringapplication.models.Cita
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CitaRepository : CoroutineCrudRepository<Cita,String> {

}