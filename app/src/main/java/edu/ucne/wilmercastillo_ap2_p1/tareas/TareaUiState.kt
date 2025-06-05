package edu.ucne.wilmercastillo_ap2_p1.tareas

import edu.ucne.wilmercastillo_ap2_p1.data.local.Entity.TareaEntity


data class TareaUiState(
    val tareaId: Int? = null,
    val descripcion: String= "",
    val minutosId: String = "",
    val errorMessage: String? = null,
    val tarea: List<TareaEntity> = emptyList()
)
