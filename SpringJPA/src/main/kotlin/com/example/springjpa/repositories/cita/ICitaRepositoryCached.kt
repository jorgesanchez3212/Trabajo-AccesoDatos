package com.example.springjpa.repositories.cita

import com.example.springjpa.models.Cita
import com.example.springjpa.repositories.CrudRepository
import java.util.UUID

interface ICitaRepositoryCached : CrudRepository<Cita,UUID> {
}