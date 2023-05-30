package com.example.springjpa.repositories.cita

import com.example.springjpa.models.Cita
import com.example.springjpa.models.Trabajador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface CitaRepository : JpaRepository<Cita,UUID> {
    suspend fun findCitasByTrabajadorAndFechaHoraBetween(trabajador: Trabajador, fechaInicio: LocalDateTime, fechaFin: LocalDateTime): List<Cita>
    suspend fun findCitasByFechaHoraBetween(fechaInicio: LocalDateTime, fechaFin: LocalDateTime): List<Cita>
}