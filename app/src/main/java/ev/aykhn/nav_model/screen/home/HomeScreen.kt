package ev.aykhn.nav_model.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ev.aykhn.nav_model.base.BaseComposableScreen
import ev.aykhn.nav_model.ui.theme.ComposeNavModelTheme

@Composable
fun HomeScreen(name: String, navController: NavController) {
    val viewModel = viewModel<HomeViewModel>()

    BaseComposableScreen(navController = navController, viewModel = viewModel) { _, _ ->
        HomeScreenContent(
            name = name,
            onButtonClick = {
                viewModel.onEvent(MainEvent.TakeMeToChurch)
            },
        )
    }
}

@Composable
private fun HomeScreenContent(
    name: String,
    onButtonClick: () -> Unit = { },
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(text = "Hello $name!")
            Button(onClick = onButtonClick) {
                Text(text = "Take me to the church")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposeNavModelTheme {
        HomeScreenContent(name = "Android")
    }
}