package edu.ucne.wilmercastillo_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.wilmercastillo_ap2_p1.presentation.sistema.SistemaListScreen
@Composable
fun HostNavigation(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.List
    ){
        composable <Screen.List>{
            SistemaListScreen()
        }
    }
}
