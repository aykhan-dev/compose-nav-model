package ev.aykhn.nav_model.base

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun <State, Event> BaseComposableScreen(
    navController: NavController,
    viewModel: BaseViewModel<State, Event>,
    onEffect: (suspend (BaseEffect) -> Unit)? = null,
    content: @Composable (activity: Activity, coroutineScope: CoroutineScope) -> Unit,
) {
    val activity = (LocalContext.current as Activity)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NavEffect.NavigateToEffect -> {
                    coroutineScope.launch {
                        navController.navigate(
                            effect.route,
                            effect.navOptions,
                            effect.navigatorExtras,
                        )
                    }
                }
                is NavEffect.NavigateBackToEffect -> {
                    coroutineScope.launch {
                        navController.popBackStack(
                            effect.destination,
                            effect.inclusive,
                        )
                    }
                }
                is NavEffect.NavigateBackEffect -> {
                    coroutineScope.launch {
                        navController.popBackStack()
                    }
                }
                is NavEffect.FinishCurrentActivity -> {
                    activity.finish()
                }
                is NavEffect.LaunchActivity -> {
                    val intent = Intent(activity, effect.destination)
                    activity.startActivity(intent)
                    if (effect.finishCurrent) {
                        activity.finish()
                    }
                }
                else -> onEffect?.invoke(effect)
            }
        }
    }

    content(activity, coroutineScope)
}