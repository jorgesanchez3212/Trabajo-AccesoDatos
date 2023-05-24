package services.citas

import io.github.reactivecircus.cache4k.Cache
import models.Cita
import java.util.*

class CitaCache {
    val cache = Cache.Builder()
        .build<String, Cita>()

}