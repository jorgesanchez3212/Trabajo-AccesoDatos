package com.example.itvspringapplication.repositories.informe

import com.example.itvspringapplication.models.Informe
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface InformeRepository : CoroutineCrudRepository<Informe,String> {
}