package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Trabajador
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("Informe")
data class InformeDTO(
    @SerialName("Trabajador_mas_gana_sin_ser_responsable")
    @XmlElement(true)
    val trabajadorMasGana : String,
    @SerialName("Salario_medio_sin_ser_responsable")
    @XmlElement(true)
    val salarioMedio : Double,
    @SerialName("Salario_medio_por_especialidad")
    @XmlElement(true)
    val salarioMedioPorEspecialidad : String,
    @SerialName("Trabajador_con_menos_antiguedad")
    @XmlElement(true)
    val trabajadorMenosAntiguedad : String,
    @SerialName("Trabajador_ordenado_especialidad_antiguedad")
    @XmlElement(true)
    val trabajadorEspecialidadAntiguedad : String

) {
}