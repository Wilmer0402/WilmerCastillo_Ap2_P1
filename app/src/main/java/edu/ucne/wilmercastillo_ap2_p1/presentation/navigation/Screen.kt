package edu.ucne.wilmercastillo_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object TareaList: Screen()

    @Serializable
    data class Tareas(val tareaId: Int?) : Screen()

}