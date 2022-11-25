package ev.aykhn.nav_model.screen.god

import androidx.lifecycle.SavedStateHandle
import ev.aykhn.nav_model.base.BaseViewModel
import ev.aykhn.nav_model.base.NavEffect

class GodViewModel(savedStateHandle: SavedStateHandle) : BaseViewModel<GodState, GodEvent>() {

    override fun provideInitialState() = GodState()

    init {
        val wishes = savedStateHandle["wishes"] ?: "none"
        emitState(state.value.copy(wishes = wishes))
    }

    override fun onEvent(event: GodEvent) {
        when (event) {
            is GodEvent.ThanksGod -> {
                emitEffect(NavEffect.FinishCurrentActivity)
            }
        }
    }

}

data class GodState(
    val wishes: String = "",
)

sealed class GodEvent {
    object ThanksGod : GodEvent()
}