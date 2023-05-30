package com.example.springjpa.services

import com.example.springjpa.models.Vehiculo
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.*
import kotlin.time.Duration.Companion.minutes

private const val STOP = 60 * 10000L

@Service
class VehiculoCache {
    val cache = Cache.Builder()
        .expireAfterWrite(1.minutes)
        .expireAfterAccess(1.minutes)
        .build<UUID, Vehiculo>()

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