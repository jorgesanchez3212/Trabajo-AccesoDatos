package services.informes

import io.github.reactivecircus.cache4k.Cache
import models.Informe
import java.util.*

class InformesCache {
    val cache = Cache.Builder()
        .build<String, Informe>()
}