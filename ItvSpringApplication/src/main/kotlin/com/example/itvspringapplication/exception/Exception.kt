package com.example.itvspringapplication.exception

class CitaException(message: String) : RuntimeException(message)
class InformeException(message: String) : RuntimeException(message)
class PropietarioException(message: String) : RuntimeException(message)
class TrabajadorException(message: String) : RuntimeException(message)
class VehiculoException(message: String) : RuntimeException(message)

class TrabajadorControllerException(message: String) : RuntimeException(message)
class PropietarioControllerException(message: String) : RuntimeException(message)
class CitaControllerException(message: String) : RuntimeException(message)
class VehiculoControllerException(message: String) : RuntimeException(message)
class InformeControllerException(message: String) : RuntimeException(message)