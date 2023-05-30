package com.example.springjpa.repositories.trabajador

import com.example.springjpa.models.Trabajador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TrabajadorRepository : JpaRepository<Trabajador,UUID>{
    fun findTrabajadorByEmail(email:String):Trabajador?
    fun findTrabajadorByUsername(username:String):Trabajador?


}