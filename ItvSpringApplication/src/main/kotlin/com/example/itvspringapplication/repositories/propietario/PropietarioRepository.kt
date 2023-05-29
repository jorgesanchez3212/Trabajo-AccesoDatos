package com.example.itvspringapplication.repositories.propietario

import com.example.itvspringapplication.models.Propietario
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PropietarioRepository : CoroutineCrudRepository<Propietario,String> {
}