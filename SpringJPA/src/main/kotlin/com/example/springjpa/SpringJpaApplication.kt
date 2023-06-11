package com.example.springjpa


import com.example.springjpa.view.ItvView
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringJpaApplication
@Autowired constructor(
    private val vista : ItvView,
) : CommandLineRunner{
    override fun run(vararg args: String?) = runBlocking {
        vista.borrarTodo()
        vista.añadirDatos()
        vista.informes()
    }

}
fun main(args: Array<String>) {
    runApplication<SpringJpaApplication>(*args)
}
