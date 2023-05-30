package com.example.springjpa.repositories.propietario

import com.example.springjpa.models.Propietario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID
@Repository
interface PropietarioRepository : JpaRepository<Propietario,UUID> {
}