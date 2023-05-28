package services.informes


import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import models.Informe
import java.util.*
import kotlin.time.Duration.Companion.minutes

private const val STOP = 6 * 10000L

class InformeCache {
    val cache = Cache.Builder()
        .expireAfterWrite(1.minutes)
        .expireAfterAccess(1.minutes)
        .build<UUID, Informe>()

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