package com.example.itvspringapplication.repositories.cita

import com.example.itvspringapplication.models.Cita
import com.example.itvspringapplication.models.Trabajador
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface CitaRepository : CoroutineCrudRepository<Cita,String> {
    suspend fun findCitasByTrabajadorAndFechaHoraBetween(trabajador: Trabajador, fechaInicio: LocalDateTime, fechaFin: LocalDateTime): List<Cita>
    suspend fun findCitasByFechaHoraBetween(fechaInicio: LocalDateTime, fechaFin: LocalDateTime): List<Cita>


}