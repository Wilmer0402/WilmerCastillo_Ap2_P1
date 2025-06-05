package edu.ucne.wilmercastillo_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.wilmercastillo_ap2_p1.tareas.TareaListScreen
import edu.ucne.wilmercastillo_ap2_p1.tareas.TareaScreen

@Composable
fun HostNavigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TareaList
    ) {
        // Pantalla lista de Tareas
        composable<Screen.TareaList> {
            TareaListScreen(
               goToTareas = { id ->
                    navHostController.navigate(Screen.Tareas(id ?: 0))
                },
                createTareas = {
                    navHostController.navigate(Screen.Tareas(0))
                }
            )
        }

        // Pantalla formulario de Tareas
        composable<Screen.Tareas> { backStack ->
            val tareaId = backStack.toRoute<Screen.Tareas>().tareaId
            TareaScreen(
                tareaId = tareaId,
                goBack = { navHostController.popBackStack() }
            )
        }
    }
}
