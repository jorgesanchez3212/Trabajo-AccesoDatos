package mapper

import dto.InformeDTO
import models.Informe


fun toInformeDTO(informe: Informe) : InformeDTO {
    return InformeDTO(
        informe.frenado,
        informe.contaminación,
        informe.aptoFrenado,
        informe.luces,
        informe.apto,
        informe.idTrabajador,
        informe.idVehiculo,
        informe.idPropietario
        )
}


fun toInforme(informe: InformeDTO) : Informe {
    return Informe(
        informe.frenado,
        informe.contaminación,
        informe.aptoFrenado,
        informe.luces,
        informe.apto,
        informe.idTrabajador,
        informe.idVehiculo,
        informe.idPropietario
    )
}