package services.citas

import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.*
import models.Cita
import java.util.*
import kotlin.time.Duration.Companion.minutes

private const val STOP = 6 * 10000L

class CitaCache {

    val cache = Cache.Builder()
        .expireAfterWrite(1.minutes)
        .expireAfterAccess(1.minutes)
        .build<String, Cita>()

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