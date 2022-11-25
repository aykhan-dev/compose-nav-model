package ev.aykhn.nav_model.screen.church

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ev.aykhn.nav_model.R
import ev.aykhn.nav_model.base.BaseComposableScreen
import ev.aykhn.nav_model.ui.theme.ComposeNavModelTheme

@Composable
fun ChurchScreen(navController: NavController) {
    val viewModel = viewModel<ChurchViewModel>()
    val scaffoldState = rememberScaffoldState()

    val state by viewModel.state.collectAsState()

    BaseComposableScreen(
        navController = navController,
        viewModel = viewModel,
        onEffect = { effect ->
            when (effect) {
                is ChurchEffect.GodListen -> scaffoldState.snackbarHostState.showSnackbar("God Listens")
            }
        }
    ) { _, _ ->
        ChurchScreenContent(
            state = state,
            scaffoldState = scaffoldState,
            onPrayToGod = {
                viewModel.onEvent(ChurchEvent.PrayToGod)
            },
            onSayWishes = { wishes ->
                viewModel.onEvent(ChurchEvent.SayWishes(wishes))
            }
        )
    }
}

@Composable
private fun ChurchScreenContent(
    state: ChurchState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onPrayToGod: () -> Unit = { },
    onSayWishes: (wishes: String) -> Unit = { },
) {
    var wishes by remember { mutableStateOf("") }

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(text = "Church")
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_location_city_24),
                contentDescription = "Church image",
                modifier = Modifier.size(90.dp),
            )
            if (state.isGodListen) {
                val isError = !state.hasWishes

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = wishes,
                        onValueChange = { wishes = it },
                        modifier = Modifier.fillMaxWidth(),
                        isError = isError,
                        singleLine = true,
                        trailingIcon = {
                            if (isError) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = "Error",
                                    tint = MaterialTheme.colors.error,
                                )
                            }
                        }
                    )
                    if (isError) {
                        Text(
                            text = "Wish something",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
            Button(onClick = {
                if (state.isGodListen) {
                    onSayWishes(wishes)
                } else {
                    onPrayToGod()
                }
            }
            ) {
                val text = if (state.isGodListen) "Say your wishes" else "Pray to God"
                Text(text = text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ComposeNavModelTheme {
        ChurchScreenContent(
            state = ChurchState()
        )
    }
}