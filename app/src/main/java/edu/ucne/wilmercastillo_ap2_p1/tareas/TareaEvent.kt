package edu.ucne.wilmercastillo_ap2_p1.tareas


sealed interface TareaEvent {
    data class TareaChange(val tareaId:Int): TareaEvent
    data class DescripcionChange(val descripcion:String): TareaEvent
    data class MinutosChange(val minutos: String) : TareaEvent
    data object Save: TareaEvent
    data object Delete: TareaEvent
    data object New: TareaEvent
}

