package com.example.itvspringapplication.repositories.propietario

import com.example.itvspringapplication.models.Propietario
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PropietarioRepository : CoroutineCrudRepository<Propietario,String> {
}