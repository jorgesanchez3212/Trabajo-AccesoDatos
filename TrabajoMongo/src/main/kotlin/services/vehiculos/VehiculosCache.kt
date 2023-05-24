package services.vehiculos

import io.github.reactivecircus.cache4k.Cache
import models.Vehiculo
import java.util.*

class VehiculosCache {
    val cache = Cache.Builder()
        .build<String, Vehiculo>()
}