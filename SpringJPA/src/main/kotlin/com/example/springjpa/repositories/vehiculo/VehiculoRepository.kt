package com.example.springjpa.repositories.vehiculo

import com.example.springjpa.models.Vehiculo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VehiculoRepository : JpaRepository<Vehiculo,UUID> {
}