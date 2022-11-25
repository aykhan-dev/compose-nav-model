package ev.aykhn.nav_model.screen.god

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ev.aykhn.nav_model.base.BaseComposableScreen
import ev.aykhn.nav_model.ui.theme.ComposeNavModelTheme

@Composable
fun GodScreen(navController: NavController) {
    val viewModel = viewModel<GodViewModel>()
    val state by viewModel.state.collectAsState()

    BaseComposableScreen(navController = navController, viewModel = viewModel) { _ , _ ->
        GodScreenContent(
            state = state,
            onButtonClick = {
                viewModel.onEvent(GodEvent.ThanksGod)
            },
        )
    }
}

@Composable
private fun GodScreenContent(
    state: GodState,
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
            Text(text = "Your \"${state.wishes}\" wishes will come true")
            Button(onClick = onButtonClick) {
                Text(text = "Thanks God!")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposeNavModelTheme {
        GodScreenContent(state = GodState())
    }
}