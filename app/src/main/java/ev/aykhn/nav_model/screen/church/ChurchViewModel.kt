package ev.aykhn.nav_model.screen.church

import ev.aykhn.nav_model.base.BaseEffect
import ev.aykhn.nav_model.base.BaseViewModel
import ev.aykhn.nav_model.base.BasicEffect

class ChurchViewModel : BaseViewModel<ChurchState, ChurchEvent>() {

    override fun provideInitialState() = ChurchState()

    override fun onEvent(event: ChurchEvent) {
        when (event) {
            is ChurchEvent.PrayToGod -> {
                emitEffect(ChurchEffect.GodListen)
                emitState(state.value.copy(isGodListen = true))
            }
            is ChurchEvent.SayWishes -> {
                val hasWishes = checkWishes(event.wishes)
                emitState(state.value.copy(hasWishes = hasWishes))

                if (hasWishes) {
                    emitEffect(BasicEffect.NavigateToEffect(route ="/god/${event.wishes}"))
                }
            }
        }
    }

    private fun checkWishes(wishes: String): Boolean {
        if (wishes.isBlank()) return false
        return true
    }

}

data class ChurchState(
    val isGodListen: Boolean = false,
    val hasWishes: Boolean = true,
)

sealed class ChurchEffect : BaseEffect() {
    object GodListen : ChurchEffect()
}

sealed class ChurchEvent {

    object PrayToGod : ChurchEvent()

    data class SayWishes(val wishes: String) : ChurchEvent()

}