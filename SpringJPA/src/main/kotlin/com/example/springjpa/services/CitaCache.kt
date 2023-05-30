package com.example.springjpa.services

import com.example.springjpa.models.Cita
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.util.*
import kotlin.time.Duration.Companion.minutes

private const val STOP = 6 * 10000L
@Service
class CitaCache {

    val cache = Cache.Builder()
        .expireAfterWrite(1.minutes)
        .expireAfterAccess(1.minutes)
        .build<UUID, Cita>()

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