package edu.ucne.wilmercastillo_ap2_p1.tareas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun TareaScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    tareaId: Int?,
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(tareaId) {
        tareaId?.let {
            if (it > 0) {
                viewModel.findTareas(it)
            }
        }
    }

    TareaBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goBack = goBack,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaBodyScreen(
    uiState: TareaUiState,
    onEvent: (TareaEvent) -> Unit,
    goBack: () -> Unit,
    viewModel: TareaViewModel
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = goBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Registro de Tareas",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = uiState.tareaId?.toString() ?: "0",
                        onValueChange = {},
                        label = { Text("ID Tarea") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    OutlinedTextField(
                        value = uiState.descripcion ?: "",
                        onValueChange = { onEvent(TareaEvent.DescripcionChange(it)) },
                        label = { Text("Descripción") },
                        placeholder = { Text("Ej: Prueba") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.minutosId,
                        onValueChange = { onEvent(TareaEvent.MinutosChange(it)) },
                        label = { Text("Minutos estimados") },
                        placeholder = { Text("Ej: 30") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors()
                    )

                    uiState.errorMessage?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { onEvent(TareaEvent.New) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Nuevo")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Nuevo")
                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    val result = viewModel.saveTarea()
                                    if (result) {
                                        goBack()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Guardar")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}
