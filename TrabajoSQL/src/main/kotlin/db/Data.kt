package db

import models.*
import java.time.LocalDate

object Data {
    val vehiculos = listOf(
        Vehiculo(
            marca = "Toyota",
            modelo = "Corolla",
            matricula = "ABC123",
            fechaMatriculacion = LocalDate.of(2022, 1, 15),
            fechaUltimaRevision = LocalDate.of(2022, 12, 30)
        ),
        Vehiculo(
            marca = "Honda",
            modelo = "Civic",
            matricula = "DEF456",
            fechaMatriculacion = LocalDate.of(2021, 8, 27),
            fechaUltimaRevision = LocalDate.of(2023, 1, 5)
        ),
        Vehiculo(
            marca = "Ford",
            modelo = "Focus",
            matricula = "GHI789",
            fechaMatriculacion = LocalDate.of(2020, 11, 10),
            fechaUltimaRevision = LocalDate.of(2022, 9, 20)
        ),
        Vehiculo(
            marca = "Volkswagen",
            modelo = "Golf",
            matricula = "JKL012",
            fechaMatriculacion = LocalDate.of(2023, 3, 5),
            fechaUltimaRevision = LocalDate.of(2023, 5, 15)
        ),
        Vehiculo(
            marca = "Chevrolet",
            modelo = "Cruze",
            matricula = "MNO345",
            fechaMatriculacion = LocalDate.of(2019, 7, 18),
            fechaUltimaRevision = LocalDate.of(2021, 12, 10)
        ),
        Vehiculo(
            marca = "BMW",
            modelo = "X5",
            matricula = "PQR678",
            fechaMatriculacion = LocalDate.of(2022, 9, 2),
            fechaUltimaRevision = LocalDate.of(2023, 4, 28)
        ),
        Vehiculo(
            marca = "Mercedes-Benz",
            modelo = "E-Class",
            matricula = "STU901",
            fechaMatriculacion = LocalDate.of(2021, 3, 12),
            fechaUltimaRevision = LocalDate.of(2022, 7, 20)
        )
    )

    val trabajadores = listOf(
        Trabajador(
            nombre = "Juan Pérez",
            telefono = 123456789,
            email = "juan@gmail.com",
            username = "juanperez",
            contraseña = "password123".toByteArray(),
            fechaContratacion = LocalDate.of(2020, 5, 15),
            especialidad = Trabajador.Especialidad.MOTOR.name,
            salario = 2800,
            responsable = true
        ),
        Trabajador(
            nombre = "María López",
            telefono = 987654321,
            email = "maria@gmail.com",
            username = "marialopez",
            contraseña = "securepass".toByteArray(),
            fechaContratacion = LocalDate.of(2021, 2, 28),
            especialidad = Trabajador.Especialidad.MECANICA.name,
            salario = 1600,
            responsable = false
        ),
        Trabajador(
            nombre = "Carlos Ramírez",
            telefono = 555555555,
            email = "carlos@gmail.com",
            username = "carlosramirez",
            contraseña = "safepassword".toByteArray(),
            fechaContratacion = LocalDate.of(2019, 10, 10),
            especialidad = Trabajador.Especialidad.ELECTRICIDAD.name,
            salario = 1900,
            responsable = false
        ),
        Trabajador(
            nombre = "Laura García",
            telefono = 111111111,
            email = "laura@gmail.com",
            username = "lauragarcia",
            contraseña = "strongpass".toByteArray(),
            fechaContratacion = LocalDate.of(2022, 6, 5),
            especialidad = Trabajador.Especialidad.INTERIOR.name,
            salario = 1750,
            responsable = false
        ),
        Trabajador(
            nombre = "Pedro Martínez",
            telefono = 999999999,
            email = "pedro@gmail.com",
            username = "pedromartinez",
            contraseña = "mypassword".toByteArray(),
            fechaContratacion = LocalDate.of(2020, 12, 1),
            especialidad = Trabajador.Especialidad.MOTOR.name,
            salario = 1700,
            responsable = false
        ),
        Trabajador(
            nombre = "Ana Torres",
            telefono = 777777777,
            email = "ana@gmail.com",
            username = "anatorres",
            contraseña = "password123".toByteArray(),
            fechaContratacion = LocalDate.of(2021, 4, 18),
            especialidad = Trabajador.Especialidad.ELECTRICIDAD.name,
            salario = 1800,
            responsable = false
        )
    )

    val propietarios = listOf(
        Propietario(
            dni = "12345678A",
            nombre = "Juan",
            apellidos = "Pérez López",
            teléfono = "123456789"
        ),
        Propietario(
            dni = "87654321B",
            nombre = "María",
            apellidos = "García Fernández",
            teléfono = "987654321"
        ),
        Propietario(
            dni = "56789012C",
            nombre = "Pedro",
            apellidos = "Martínez Sánchez",
            teléfono = "555555555"
        ),
        Propietario(
            dni = "90123456D",
            nombre = "Laura",
            apellidos = "Torres Rodríguez",
            teléfono = "111111111"
        ),
        Propietario(
            dni = "34567890E",
            nombre = "Ana",
            apellidos = "López Navarro",
            teléfono = "999999999"
        ),
        Propietario(
            dni = "67890123F",
            nombre = "Carlos",
            apellidos = "Gómez Ruiz",
            teléfono = "777777777"
        ),
        Propietario(
            dni = "23456789G",
            nombre = "Sara",
            apellidos = "Hernández García",
            teléfono = "444444444"
        )
    )
}