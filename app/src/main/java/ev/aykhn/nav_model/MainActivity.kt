package ev.aykhn.nav_model

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ev.aykhn.nav_model.screen.church.ChurchScreen
import ev.aykhn.nav_model.screen.god.GodScreen
import ev.aykhn.nav_model.screen.home.HomeScreen
import ev.aykhn.nav_model.ui.theme.ComposeNavModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Router()
                }
            }
        }
    }
}

@Composable
fun Router() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "/") {
        composable("/") { HomeScreen(name = "Android", navController = navController) }
        composable("/church") { ChurchScreen(navController = navController) }
        composable("/god/{wishes}") { GodScreen(navController = navController) }
    }
}