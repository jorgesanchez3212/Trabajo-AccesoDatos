package services.vehiculos

import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import models.Vehiculo
import kotlin.time.Duration.Companion.minutes

private const val STOP = 60 * 10000L

class VehiculosCache {
    val cache = Cache.Builder()
        .expireAfterWrite(1.minutes)
        .expireAfterAccess(1.minutes)
        .build<String, Vehiculo>()

    suspend fun refresh() {
        withContext(newSingleThreadContext("cache")) {
            launch {
                println("ACTUALIZANDO CACHE")
                cache.invalidateAll()
                delay(STOP)
            }
        }
    }
}