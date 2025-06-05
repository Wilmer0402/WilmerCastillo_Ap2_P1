package edu.ucne.wilmercastillo_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.wilmercastillo_ap2_p1.data.local.Database.TareaDb
import edu.ucne.wilmercastillo_ap2_p1.presentation.navigation.HostNavigation
import edu.ucne.wilmercastillo_ap2_p1.ui.theme.WilmerCastillo_Ap2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var tareaDb: TareaDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tareaDb = Room.databaseBuilder(
            applicationContext,
           TareaDb::class.java,
            "Sistema1.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            WilmerCastillo_Ap2_P1Theme {
                val nav = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        HostNavigation(
                            navHostController = nav
                        )
                    }
                }
            }
        }
    }
}



