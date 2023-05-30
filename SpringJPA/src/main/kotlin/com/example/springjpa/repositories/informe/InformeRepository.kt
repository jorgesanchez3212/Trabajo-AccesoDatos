package com.example.springjpa.repositories.informe

import com.example.springjpa.models.Informe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface InformeRepository : JpaRepository<Informe,UUID> {
}