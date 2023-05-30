package com.example.itvspringapplication.repositories.informe

import com.example.itvspringapplication.models.Informe
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InformeRepository : CoroutineCrudRepository<Informe,String> {
}