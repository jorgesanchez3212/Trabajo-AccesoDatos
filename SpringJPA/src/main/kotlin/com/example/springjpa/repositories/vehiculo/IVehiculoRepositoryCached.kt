package com.example.springjpa.repositories.vehiculo

import com.example.springjpa.models.Vehiculo
import com.example.springjpa.repositories.CrudRepository
import java.util.UUID

interface IVehiculoRepositoryCached : CrudRepository<Vehiculo,UUID> {
}