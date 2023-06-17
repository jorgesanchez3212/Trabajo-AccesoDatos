package com.example.itvspringapplication

import com.example.itvspringapplication.view.ItvView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class ItvSpringApplication
@Autowired constructor(
    private val vista : ItvView,
) : CommandLineRunner{
    override fun run(vararg args: String?) = runBlocking {
        println("💻Bienvenido a la ITV...😎")

        CoroutineScope(Dispatchers.IO).launch {
            vista.state()
        }
        vista.borrarTodo()
        vista.menu()
    }

}

fun main(args: Array<String>) {
    runApplication<ItvSpringApplication>(*args)
}
