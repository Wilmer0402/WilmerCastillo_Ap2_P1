package edu.ucne.wilmercastillo_ap2_p1.tareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.wilmercastillo_ap2_p1.data.local.Entity.TareaEntity
import edu.ucne.wilmercastillo_ap2_p1.data.repository.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val tareaRepository: TareaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTareas()
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            TareaEvent.Delete -> deleteTarea()
            TareaEvent.New -> nuevo()
            is TareaEvent.DescripcionChange -> onDescripcionChange(event.descripcion)
            is TareaEvent.MinutosChange -> onMinutosChange(event.minutos)
            is TareaEvent.TareaChange -> onTareaIdChange(event.tareaId)
            TareaEvent.Save -> viewModelScope.launch { saveTarea() }
        }
    }

    private fun nuevo() {
        _uiState.update {
            it.copy(
                tareaId = null,
                descripcion = "",
                minutosId = "",
                errorMessage = null
            )
        }
    }

    private fun getTareas() {
        viewModelScope.launch {
            tareaRepository.getAll().collect { tareas ->
                _uiState.update {
                    it.copy(tarea = tareas)
                }
            }
        }
    }

    fun findTareas(tareaId: Int) {
        viewModelScope.launch {
            if (tareaId > 0) {
                val tarea = tareaRepository.find(tareaId)
                _uiState.update {
                    it.copy(
                        tareaId = tarea?.tareaId,
                        descripcion = tarea?.descripcion ?: "",
                        minutosId = tarea?.minutosId?.toString() ?: ""
                    )
                }
            }
        }
    }

    suspend fun saveTarea(): Boolean {
        val currentState = _uiState.value
        val minutos = currentState.minutosId.toIntOrNull()

        return if (currentState.descripcion.isBlank() || minutos == null) {
            _uiState.update {
                it.copy(errorMessage = "Campos inválidos")
            }
            false
        } else {
            tareaRepository.save(
                TareaEntity(
                    tareaId = currentState.tareaId,
                    descripcion = currentState.descripcion,
                    minutosId = minutos
                )
            )
            _uiState.update {
                it.copy(errorMessage = null)
            }
            true
        }
    }

    private fun deleteTarea() {
        viewModelScope.launch {
            tareaRepository.delete(_uiState.value.toEntity())
        }
    }

    private fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    private fun onMinutosChange(minutos: String) {
        _uiState.update {
            it.copy(minutosId = minutos)
        }
    }

    private fun onTareaIdChange(tareaId: Int) {
        _uiState.update {
            it.copy(tareaId = tareaId)
        }
    }
}

fun TareaUiState.toEntity() = TareaEntity(
    tareaId = tareaId,
    minutosId = minutosId.toIntOrNull() ?: 0,
    descripcion = descripcion
)




