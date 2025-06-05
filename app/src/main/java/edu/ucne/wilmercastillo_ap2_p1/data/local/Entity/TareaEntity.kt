package edu.ucne.wilmercastillo_ap2_p1.data.local.Entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Tareas")
data class TareaEntity(
    @PrimaryKey
    val tareaId: Int? = 0,
    val descripcion: String = "",
    val minutosId: Int? = 0
)