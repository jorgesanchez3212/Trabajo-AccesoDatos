package repositories.vehiculo

import models.Vehiculo

class VehiculoRepository : IVehiculoRepository {
    override fun findAll(): List<Vehiculo> {
        var vehiculos = mutableListOf<Vehiculo>()
        HibernateManage
    }

    override fun findById(id: Int): Vehiculo? {
        TODO("Not yet implemented")
    }

    override fun save(entity: Vehiculo): Vehiculo {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Vehiculo): Boolean {
        TODO("Not yet implemented")
    }
}