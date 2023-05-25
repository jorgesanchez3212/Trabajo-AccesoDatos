package db

import models.*
import java.time.LocalDate
import java.time.LocalDateTime

object Data {
    val vehiculos = listOf(
        Vehiculo(
            _id = "1",
            marca = "Toyota",
            modelo = "Corolla",
            matricula = "ABC123",
            fechaMatriculacion = LocalDate.of(2022, 1, 15),
            fechaUltimaRevision = LocalDate.of(2022, 12, 30)
        ),
        Vehiculo(
            _id = "2",
            marca = "Honda",
            modelo = "Civic",
            matricula = "DEF456",
            fechaMatriculacion = LocalDate.of(2021, 8, 27),
            fechaUltimaRevision = LocalDate.of(2023, 1, 5)
        ),
        Vehiculo(
            _id = "3",
            marca = "Ford",
            modelo = "Focus",
            matricula = "GHI789",
            fechaMatriculacion = LocalDate.of(2020, 11, 10),
            fechaUltimaRevision = LocalDate.of(2022, 9, 20)
        ),
        Vehiculo(
            _id = "4",
            marca = "Volkswagen",
            modelo = "Golf",
            matricula = "JKL012",
            fechaMatriculacion = LocalDate.of(2023, 3, 5),
            fechaUltimaRevision = LocalDate.of(2023, 5, 15)
        ),
        Vehiculo(
            _id = "5",
            marca = "Chevrolet",
            modelo = "Cruze",
            matricula = "MNO345",
            fechaMatriculacion = LocalDate.of(2019, 7, 18),
            fechaUltimaRevision = LocalDate.of(2021, 12, 10)
        ),
        Vehiculo(
            _id = "6",
            marca = "BMW",
            modelo = "X5",
            matricula = "PQR678",
            fechaMatriculacion = LocalDate.of(2022, 9, 2),
            fechaUltimaRevision = LocalDate.of(2023, 4, 28)
        ),
        Vehiculo(
            _id = "7",
            marca = "Mercedes-Benz",
            modelo = "E-Class",
            matricula = "STU901",
            fechaMatriculacion = LocalDate.of(2021, 3, 12),
            fechaUltimaRevision = LocalDate.of(2022, 7, 20)
        )
    )

    val trabajadores = listOf(
        Trabajador(
            _id = "1",
            nombre = "Juan Pérez",
            teléfono = 123456789,
            email = "juan@gmail.com",
            username = "juanperez",
            contraseña = "password123".toByteArray(),
            fechaContratacion = LocalDate.of(2020, 5, 15),
            especialidad = Trabajador.Especialidad.MOTOR.name,
            salario = 2800,
            responsable = true
        ),
        Trabajador(
            _id = "2",
            nombre = "María López",
            teléfono = 987654321,
            email = "maria@gmail.com",
            username = "marialopez",
            contraseña = "securepass".toByteArray(),
            fechaContratacion = LocalDate.of(2021, 2, 28),
            especialidad = Trabajador.Especialidad.MECANICA.name,
            salario = 1600,
            responsable = false
        ),
        Trabajador(
            _id = "3",
            nombre = "Carlos Ramírez",
            teléfono = 555555555,
            email = "carlos@gmail.com",
            username = "carlosramirez",
            contraseña = "safepassword".toByteArray(),
            fechaContratacion = LocalDate.of(2019, 10, 10),
            especialidad = Trabajador.Especialidad.ELECTRICIDAD.name,
            salario = 1900,
            responsable = false
        ),
        Trabajador(
            _id = "4",
            nombre = "Laura García",
            teléfono = 111111111,
            email = "laura@gmail.com",
            username = "lauragarcia",
            contraseña = "strongpass".toByteArray(),
            fechaContratacion = LocalDate.of(2022, 6, 5),
            especialidad = Trabajador.Especialidad.INTERIOR.name,
            salario = 1750,
            responsable = false
        ),
        Trabajador(
            _id = "5",
            nombre = "Pedro Martínez",
            teléfono = 999999999,
            email = "pedro@gmail.com",
            username = "pedromartinez",
            contraseña = "mypassword".toByteArray(),
            fechaContratacion = LocalDate.of(2020, 12, 1),
            especialidad = Trabajador.Especialidad.MOTOR.name,
            salario = 1700,
            responsable = false
        ),
        Trabajador(
            _id = "6",
            nombre = "Ana Torres",
            teléfono = 777777777,
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
            _id = "1",
            dni = "12345678A",
            nombre = "Juan",
            apellidos = "Pérez López",
            teléfono = "123456789"
        ),
        Propietario(
            _id = "2",
            dni = "87654321B",
            nombre = "María",
            apellidos = "García Fernández",
            teléfono = "987654321"
        ),
        Propietario(
            _id = "3",
            dni = "56789012C",
            nombre = "Pedro",
            apellidos = "Martínez Sánchez",
            teléfono = "555555555"
        ),
        Propietario(
            _id = "4",
            dni = "90123456D",
            nombre = "Laura",
            apellidos = "Torres Rodríguez",
            teléfono = "111111111"
        ),
        Propietario(
            _id = "5",
            dni = "34567890E",
            nombre = "Ana",
            apellidos = "López Navarro",
            teléfono = "999999999"
        ),
        Propietario(
            _id = "6",
            dni = "67890123F",
            nombre = "Carlos",
            apellidos = "Gómez Ruiz",
            teléfono = "777777777"
        ),
        Propietario(
            _id = "7",
            dni = "23456789G",
            nombre = "Sara",
            apellidos = "Hernández García",
            teléfono = "444444444"
        )
    )

    val informes = listOf(
        Informe(
            frenado = 5,
            contaminación = 2.1,
            aptoFrenado = true,
            luces = true,
            apto = true,
            idTrabajador = "1",
            idVehiculo = "1",
            idPropietario = "1"
        ),
        Informe(
            frenado = 3,
            contaminación = 1.8,
            aptoFrenado = false,
            luces = true,
            apto = false,
            idTrabajador = "2",
            idVehiculo = "2",
            idPropietario = "2"
        ),
        Informe(
            frenado = 4,
            contaminación = 1.5,
            aptoFrenado = true,
            luces = false,
            apto = false,
            idTrabajador = "3",
            idVehiculo = "3",
            idPropietario = "3"
        ),
        Informe(
            frenado = 2,
            contaminación = 3.2,
            aptoFrenado = false,
            luces = true,
            apto = false,
            idTrabajador = "4",
            idVehiculo = "4",
            idPropietario = "4"
        ),
        Informe(
            frenado = 5,
            contaminación = 2.5,
            aptoFrenado = true,
            luces = true,
            apto = true,
            idTrabajador = "5",
            idVehiculo = "5",
            idPropietario = "5"
        ),
        Informe(
            frenado = 3,
            contaminación = 1.9,
            aptoFrenado = false,
            luces = false,
            apto = false,
            idTrabajador = "6",
            idVehiculo = "6",
            idPropietario = "6"
        ),
        Informe(
            frenado = 4,
            contaminación = 1.4,
            aptoFrenado = true,
            luces = true,
            apto = false,
            idTrabajador = "7",
            idVehiculo = "7",
            idPropietario = "7"
        )
    )

    val fechaInicio = LocalDateTime.now()

    val citas = listOf(
        Cita(
            fechaHora = fechaInicio.plusMinutes(30),
            idTrabajador = "1",
            idVehiculo = "1",
            idPropietario = "1"
        ),
        Cita(
            fechaHora = fechaInicio.plusMinutes(60),
            idTrabajador = "2",
            idVehiculo = "2",
            idPropietario = "2"
        ),
        Cita(
            fechaHora = fechaInicio.plusMinutes(90),
            idTrabajador = "3",
            idVehiculo = "3",
            idPropietario = "3"
        ),
        Cita(
            fechaHora = fechaInicio.plusMinutes(120),
            idTrabajador = "4",
            idVehiculo = "4",
            idPropietario = "4"
        ),
        Cita(
            fechaHora = fechaInicio.plusMinutes(150),
            idTrabajador = "5",
            idVehiculo = "5",
            idPropietario = "5"
        ),
        Cita(
            fechaHora = fechaInicio.plusMinutes(180),
            idTrabajador = "6",
            idVehiculo = "6",
            idPropietario = "6"
        ),
        Cita(
            fechaHora = fechaInicio.plusMinutes(210),
            idTrabajador = "7",
            idVehiculo = "7",
            idPropietario = "7"
        )
    )
}