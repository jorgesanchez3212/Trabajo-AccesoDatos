package com.example.itvspringapplication

import com.example.itvspringapplication.services.reactive.Watchers
import com.example.itvspringapplication.view.ItvView
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ItvSpringApplication
@Autowired constructor(
    private val vista : ItvView,
    private val watchers : Watchers
) : CommandLineRunner{
    override fun run(vararg args: String?) = runBlocking {
        vista.opciones()
    }

}

fun main(args: Array<String>) {
    runApplication<ItvSpringApplication>(*args)
}
