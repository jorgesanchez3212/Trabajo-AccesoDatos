package com.example.springjpa.repositories.informe

import com.example.springjpa.models.Informe
import com.example.springjpa.repositories.CrudRepository
import java.util.UUID

interface IInformeRepositoryCached : CrudRepository<Informe,UUID> {
}