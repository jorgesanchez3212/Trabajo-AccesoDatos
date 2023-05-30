package com.example.itvspringapplication.services.reactive

import com.example.itvspringapplication.models.Cita
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ChangeStreamEvent
import org.springframework.data.mongodb.core.ChangeStreamOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {  }

@Service
class Watchers
@Autowired constructor(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
){

    fun watchOrder() : Flow<ChangeStreamEvent<Cita>> {
        logger.info { "watching Order" }
        return reactiveMongoTemplate.changeStream(
            "cita",
            ChangeStreamOptions.empty(),
            Cita::class.java
        ).asFlow()
    }

}