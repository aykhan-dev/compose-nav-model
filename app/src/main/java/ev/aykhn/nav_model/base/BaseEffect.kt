package ev.aykhn.nav_model.base

import android.content.Intent
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

abstract class BaseEffect

sealed class NavEffect : BaseEffect() {

    data class NavigateToEffect(
        val route: String,
        val navOptions: NavOptions? = null,
        val navigatorExtras: Navigator.Extras? = null,
    ) : BaseEffect()

    data class NavigateBackToEffect(
        val destination: String,
        val inclusive: Boolean = false,
        val navOptions: NavOptions? = null,
        val navigatorExtras: Navigator.Extras? = null,
    ) : BaseEffect()

    object NavigateBackEffect : BaseEffect()

    object FinishCurrentActivity : NavEffect()

    data class LaunchActivity(val destination: Class<*>, val finishCurrent: Boolean = false): NavEffect()

}