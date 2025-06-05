package edu.ucne.wilmercastillo_ap2_p1.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import edu.ucne.wilmercastillo_ap2_p1.data.local.Entity.TareaEntity

@Composable
fun TareaListScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    goToTareas: (Int) -> Unit,
    createTareas: () -> Unit,
    deleteTareas: ((TareaEntity) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TareaListBodyScreen(
        uiState = uiState,
        goToTareas = goToTareas,
        createTareas = createTareas,
        deleteTareas = { tarea ->
            viewModel.onEvent(TareaEvent.TareaChange(tarea.tareaId ?: 0))
            viewModel.onEvent(TareaEvent.Delete)
        }
    )
}

@Composable
fun TareaRow(
    tarea: TareaEntity,
    goToTareas: (Int) -> Unit,
    deleteTarea: (TareaEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Tarea ID: ${tarea.tareaId ?: 0}", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Descripción: ${tarea.descripcion}", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Minutos: ${tarea.minutosId}", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { goToTareas(tarea.tareaId ?: 0) },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { deleteTarea(tarea) },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun TareaListBodyScreen(
    uiState: TareaUiState,
    goToTareas: (Int) -> Unit,
    createTareas: () -> Unit,
    deleteTareas: (TareaEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Tareas") },
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createTareas) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Tarea")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(uiState.tarea) { tarea ->
                TareaRow(
                    tarea = tarea,
                    goToTareas = goToTareas,
                    deleteTarea = deleteTareas
                )
            }
        }
    }
}
